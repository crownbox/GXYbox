package com.gxy.reader.page;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.eschao.android.widget.pageflip.Page;
import com.eschao.android.widget.pageflip.PageFlip;
import com.eschao.android.widget.pageflip.PageFlipState;
import com.gxy.reader.Data.Const;

import java.io.File;

/**
 * Created by Administrator on 2018/7/13.
 */

public class SinglePageRender extends PageRender {
    private int CurrentPage=1;
    public SinglePageRender(Context context, PageFlip pageFlip, Handler handler, int pageNo) {
        super(context, pageFlip, handler, pageNo);
    }

    @Override
    void onDrawFrame() {
// 1. delete unused textures
        mPageFlip.deleteUnusedTextures();
        Page page = mPageFlip.getFirstPage();

        // 2. handle drawing command triggered from finger moving and animating
        if (mDrawCommand == DRAW_MOVING_FRAME ||
                mDrawCommand == DRAW_ANIMATING_FRAME) {
            // is forward flip
            if (mPageFlip.getFlipState() == PageFlipState.FORWARD_FLIP) {
                // check if second texture of first page is valid, if not,
                // create new one
                if (!page.isSecondTextureSet()) {
                    drawPage(mPageNo + 1);
                    page.setSecondTexture(mBitmap);
                }
            }
            // in backward flip, check first texture of first page is valid
            else if (!page.isFirstTextureSet()) {
                drawPage(--mPageNo);
                page.setFirstTexture(mBitmap);
            }

            // draw frame for page flip
            mPageFlip.drawFlipFrame();
        }
        // draw stationary page without flipping
        else if (mDrawCommand == DRAW_FULL_PAGE) {
            if (!page.isFirstTextureSet()) {
                drawPage(mPageNo);
                page.setFirstTexture(mBitmap);
            }

            mPageFlip.drawPageFrame();
        }

        // 3. send message to main thread to notify drawing is ended so that
        // we can continue to calculate next animation frame if need.
        // Remember: the drawing operation is always in GL thread instead of
        // main thread
        Message msg = Message.obtain();
        msg.what = MSG_ENDED_DRAWING_FRAME;
        msg.arg1 = mDrawCommand;
        mHandler.sendMessage(msg);
    }
    /**
     * Handle GL surface is changed
     *
     * @param width surface width
     * @param height surface height
     */
    @Override
    void onSurfaceChanged(int width, int height) {
// recycle bitmap resources if need
        if (mBackgroundBitmap != null) {
            mBackgroundBitmap.recycle();
        }

        if (mBitmap != null) {
            mBitmap.recycle();
        }

        // create bitmap and canvas for page
        //mBackgroundBitmap = background;
        Page page = mPageFlip.getFirstPage();
        mBitmap = Bitmap.createBitmap((int)page.width(), (int)page.height(),
                Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
        LoadBitmapTask.get(mContext).set(width, height, 1);
    }
    /**
     * Handle ended drawing event
     * In here, we only tackle the animation drawing event, If we need to
     * continue requesting render, please return true. Remember this function
     * will be called in main thread
     *
     * @param what event type
     * @return ture if need render again
     */
    @Override
    boolean onEndedDrawing(int what) {
        if (what == DRAW_ANIMATING_FRAME) {
            boolean isAnimating = mPageFlip.animating();
            // continue animating
            if (isAnimating) {
                mDrawCommand = DRAW_ANIMATING_FRAME;
                return true;
            }
            // animation is finished
            else {
                final PageFlipState state = mPageFlip.getFlipState();
                // update page number for backward flip
                if (state == PageFlipState.END_WITH_BACKWARD) {
                    // don't do anything on page number since mPageNo is always
                    // represents the FIRST_TEXTURE no;
                }
                // update page number and switch textures for forward flip
                else if (state == PageFlipState.END_WITH_FORWARD) {
                    mPageFlip.getFirstPage().setFirstTextureWithSecond();
                    mPageNo++;
                }

                mDrawCommand = DRAW_FULL_PAGE;
                return true;
            }
        }
        return false;
    }

    /**
     * If page can flip forward
     *
     * @return true if it can flip forward
     */
    @Override
    public boolean canFlipForward() {
        return true;
    }

    /**
     * If page can flip backward
     *
     * @return true if it can flip backward
     */
    @Override
    public boolean canFlipBackward() {
        if (mPageNo > 1) {
            mPageFlip.getFirstPage().setSecondTextureWithFirst();
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Draw page content
     *
     * @param number page number
     */
    private void drawPage(int number) {
        LogUtils.e("drawPage"+number);
        final int width = mCanvas.getWidth();
        final int height = mCanvas.getHeight();
        Paint p = new Paint();
        p.setFilterBitmap(true);

        // 1. draw background bitmap
        Bitmap background = LoadBitmapTask.get(mContext).getBitmap();
        Rect rect = new Rect(0, 0, width, height);
        mCanvas.drawBitmap(background, null, rect, p);
        background.recycle();
        background = null;

        // 2. draw page number
        int fontSize = calcFontSize(24);
        p.setColor(Color.WHITE);
        p.setStrokeWidth(1);
        p.setAntiAlias(true);
        p.setShadowLayer(5.0f, 8.0f, 8.0f, Color.BLACK);
        p.setTextSize(fontSize);

        String text = String.valueOf(FileIOUtils.readFile2String(new File(Const.PATH_MAIN+"/book.txt"),"gbk"));
        int textLength=text.length();
        int screenWidth= ScreenUtils.getScreenWidth();
        int screenHeight=ScreenUtils.getScreenHeight();

        int rowNum=(int)Math.floor(screenHeight/(fontSize+10));//每页行数,减去1是为因为最下面的一行字会被遮住半个，索性去掉，搬到第二页去,加10是为了每行之间有10个dp的间距

        int lineTextNum=screenWidth/fontSize;//每行字数

        int pageTextNum=lineTextNum*rowNum;//每页总字数
        int rows=textLength/lineTextNum;//行数等于总字数除以每行字数

        int begin=(number-1)*pageTextNum;
        int end=lineTextNum+begin;

        int x=0;
        int y=0+ BarUtils.getStatusBarHeight();
        for(int i=0;i<rowNum;i++) {
         //   float textWidth = p.measureText(text);
         //   float y = height - p.getTextSize() - 20;
         //   mCanvas.drawText(text.substring(begin,end), 0, p.getTextSize(), p);
            mCanvas.drawText(text,begin,end,x,y,p);
            y+=fontSize+10;
            begin=end;
            end+=lineTextNum;
            if(end>textLength){
                end=textLength;
            }
        }

       /* if (number <= 1) {
            String firstPage = "The First Page";
            p.setTextSize(calcFontSize(16));
            float w = p.measureText(firstPage);
            float h = p.getTextSize();
            mCanvas.drawText(firstPage, (width - w) / 2, y + 5 + h, p);
        }
        else if (number >= MAX_PAGES) {
            String lastPage = "The Last Page";
            p.setTextSize(calcFontSize(16));
            float w = p.measureText(lastPage);
            float h = p.getTextSize();
            mCanvas.drawText(lastPage, (width - w) / 2, y + 5 + h, p);
        }*/
    }
}
