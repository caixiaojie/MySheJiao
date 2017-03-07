package com.flyingfish.base;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyingfish.R;

/**
 * 所有页面的父类
 * @author dongli
 *
 */
public class BaseActivity extends FragmentActivity implements OnClickListener{
	

	
	/**+++ for title bar +++*/
    protected ImageView mLeftIcon, mRightBtn,mSearchBtn,mAddBtn,mMoreBtn;
    //protected LinearLayout mRightBtn;
    protected TextView titileTextView,mFristTitlte,mTrowTitle,mRightTextBtn;
    protected RelativeLayout mFirstLayout;
    protected LinearLayout mLeftBtn,mCenterLayout;
    
    protected void setTitleContent(int left_src_id, int right_src_id, int title_id){
        mLeftBtn = (LinearLayout)findViewById(R.id.left_btn);
        mLeftIcon = (ImageView)findViewById(R.id.left_icon);
        mRightBtn = (ImageView)findViewById(R.id.right_btn);
        mRightTextBtn = (TextView)findViewById(R.id.right_text_btn);
        //mRightBtn = (LinearLayout)findViewById(R.id.right_btn);
        mSearchBtn = (ImageView)findViewById(R.id.search_btn);
        mAddBtn = (ImageView)findViewById(R.id.add_btn);
        mMoreBtn = (ImageView)findViewById(R.id.more_btn);
        
        titileTextView = (TextView)findViewById(R.id.title);
        
        if (left_src_id != 0) {
            mLeftIcon = (ImageView)findViewById(R.id.left_icon);
        	mLeftIcon.setImageResource(left_src_id);
            mLeftIcon.setVisibility(View.VISIBLE);
        }
        
        if (right_src_id != 0) {
        	mRightBtn.setImageResource(right_src_id);
        	mRightBtn.setVisibility(View.VISIBLE);
        }
        
        if (title_id != 0) {
            titileTextView.setText(title_id);
        }
    }
    
    protected void setTrowMenuTitleContent(int left_src_id, int right_src_id, 
    		String firstTitlte,String trowTitle){
        mLeftBtn = (LinearLayout)findViewById(R.id.left_btn);
        mLeftIcon = (ImageView)findViewById(R.id.left_icon);
        mRightBtn = (ImageView)findViewById(R.id.right_btn);
        mRightTextBtn = (TextView)findViewById(R.id.right_text_btn);
        //mRightBtn = (LinearLayout)findViewById(R.id.right_btn);
        mSearchBtn = (ImageView)findViewById(R.id.search_btn);
        mAddBtn = (ImageView)findViewById(R.id.add_btn);
        mMoreBtn = (ImageView)findViewById(R.id.more_btn);
        
        titileTextView = (TextView)findViewById(R.id.title);
        titileTextView.setVisibility(View.GONE);
        
        mFristTitlte= (TextView)findViewById(R.id.other_title);
        mTrowTitle= (TextView)findViewById(R.id.child_title);
        if(firstTitlte!=null && !firstTitlte.equals("")){
        	mFristTitlte.setText(firstTitlte);
        	mFristTitlte.setVisibility(View.VISIBLE);
        }
        if(trowTitle!=null && !trowTitle.equals("")){
        	mTrowTitle.setText(trowTitle);
        	mTrowTitle.setVisibility(View.VISIBLE);
        }
        if (left_src_id != 0) {
        	mLeftIcon.setImageResource(left_src_id);
        }
        
        if (right_src_id != 0) {
        	mRightBtn.setImageResource(right_src_id);
        	mRightBtn.setVisibility(View.VISIBLE);
        }
        
       
    }
    
    protected void setTitleContent(int left_src_id,boolean isShowSearch, int right_src_id, int title_id){
        mLeftBtn = (LinearLayout)findViewById(R.id.left_btn);
        mLeftIcon = (ImageView)findViewById(R.id.left_icon);
        mRightBtn = (ImageView)findViewById(R.id.right_btn);
        mRightTextBtn = (TextView)findViewById(R.id.right_text_btn);
        //mRightBtn = (LinearLayout)findViewById(R.id.right_btn);
        mSearchBtn = (ImageView)findViewById(R.id.search_btn);
        mAddBtn = (ImageView)findViewById(R.id.add_btn);
        mMoreBtn = (ImageView)findViewById(R.id.more_btn);
        
        titileTextView = (TextView)findViewById(R.id.title);
        
        if (left_src_id != 0) {
        	mLeftIcon.setImageResource(left_src_id);
        }
        
        if(isShowSearch){
        	mSearchBtn.setVisibility(View.VISIBLE);
        }
        
        if (right_src_id != 0) {
        	mRightBtn.setImageResource(right_src_id);
        	mRightBtn.setVisibility(View.VISIBLE);
        }
        
        if (title_id != 0) {
            titileTextView.setText(title_id);
        }
    }
    
    protected void setTitleContent(int left_src_id, boolean showSearchIcon,
    		boolean showAddIcon,boolean showMoreIcon,int title_id){
        mLeftBtn = (LinearLayout)findViewById(R.id.left_btn);
        mLeftIcon = (ImageView)findViewById(R.id.left_icon);
        mRightBtn = (ImageView)findViewById(R.id.right_btn);
        mRightTextBtn = (TextView)findViewById(R.id.right_text_btn);
        //mRightBtn = (LinearLayout)findViewById(R.id.right_btn);
        mSearchBtn = (ImageView)findViewById(R.id.search_btn);
        mAddBtn = (ImageView)findViewById(R.id.add_btn);
        mMoreBtn = (ImageView)findViewById(R.id.more_btn);
        titileTextView = (TextView)findViewById(R.id.title);
        mCenterLayout = (LinearLayout)findViewById(R.id.center_layout);
        
        if (left_src_id != 0) {
        	mLeftIcon.setImageResource(left_src_id);
        }
        if(showSearchIcon){
        	mSearchBtn.setVisibility(View.VISIBLE);
        }
       
        if(showAddIcon){
        	mAddBtn.setVisibility(View.VISIBLE);
        }
        if(showMoreIcon){
        	mMoreBtn.setVisibility(View.VISIBLE);
        }
        
        
        if (title_id != 0) {
            titileTextView.setText(title_id);
        }
    }
    
    
    protected void setRightTextTitleContent(int left_src_id, String right_src_id, int title_id){
        mLeftBtn = (LinearLayout)findViewById(R.id.left_btn);
        mLeftIcon = (ImageView)findViewById(R.id.left_icon);
        mRightBtn = (ImageView)findViewById(R.id.right_btn);
        mRightTextBtn = (TextView)findViewById(R.id.right_text_btn);
        //mRightBtn = (LinearLayout)findViewById(R.id.right_btn);
        mSearchBtn = (ImageView)findViewById(R.id.search_btn);
        mAddBtn = (ImageView)findViewById(R.id.add_btn);
        mMoreBtn = (ImageView)findViewById(R.id.more_btn);
        
        titileTextView = (TextView)findViewById(R.id.title);
        
        if (left_src_id != 0) {
        	mLeftIcon.setImageResource(left_src_id);
        }
        
        if (right_src_id != null && right_src_id.equals("")) {
        	mRightTextBtn.setText(right_src_id);
        	mRightTextBtn.setVisibility(View.VISIBLE);
        }
        
        if (title_id != 0) {
            titileTextView.setText(title_id);
        }
    }
    
    
    protected void setRightTextTitleContent(int left_src_id, int right_src_id, int title_id){
        mLeftBtn = (LinearLayout)findViewById(R.id.left_btn);
        mLeftIcon = (ImageView)findViewById(R.id.left_icon);
        mRightBtn = (ImageView)findViewById(R.id.right_btn);
        mRightTextBtn = (TextView)findViewById(R.id.right_text_btn);
        //mRightBtn = (LinearLayout)findViewById(R.id.right_btn);
        mSearchBtn = (ImageView)findViewById(R.id.search_btn);
        mAddBtn = (ImageView)findViewById(R.id.add_btn);
        mMoreBtn = (ImageView)findViewById(R.id.more_btn);
        
        titileTextView = (TextView)findViewById(R.id.title);
        
        if (left_src_id != 0) {
        	mLeftIcon.setImageResource(left_src_id);
        }
        
        if (right_src_id != 0) {
        	mRightTextBtn.setText(right_src_id);
        	mRightTextBtn.setVisibility(View.VISIBLE);
        }
        
        if (title_id != 0) {
            titileTextView.setText(title_id);
        }
    }
    
    protected void setTitleContent(int left_src_id, int right_src_id, String title_text){
        mLeftBtn = (LinearLayout)findViewById(R.id.left_btn);
        mLeftIcon = (ImageView)findViewById(R.id.left_icon);
        mRightBtn = (ImageView)findViewById(R.id.right_btn);
        mRightTextBtn = (TextView)findViewById(R.id.right_text_btn);
        mSearchBtn = (ImageView)findViewById(R.id.search_btn);
        mAddBtn = (ImageView)findViewById(R.id.add_btn);
        mMoreBtn = (ImageView)findViewById(R.id.more_btn);
        titileTextView = (TextView)findViewById(R.id.title);
       // mRightBtn = (LinearLayout)findViewById(R.id.right_btn);

        if (left_src_id != 0) {
            mLeftIcon.setVisibility(View.VISIBLE);
        	mLeftIcon.setImageResource(R.mipmap.go_back);
        }
        
        if (right_src_id != 0) {
        	mRightBtn.setImageResource(right_src_id);
        	mRightBtn.setVisibility(View.VISIBLE);
        }
        
        if (title_text != null && !title_text.equals("")) {
            titileTextView.setText(title_text);
        }
    }
    
    /**--- for title bar ---*/
    @Override
    protected void onStop() {
        super.onStop();
     }

    @Override
    protected void onResume() {
        super.onResume();
     }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_btn:{
                finish();
            }break;
        }
    }
}
