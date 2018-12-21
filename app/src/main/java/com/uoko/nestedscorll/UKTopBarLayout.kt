package com.uoko.landlord.widget

import android.content.Context
import android.graphics.Typeface
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.NestedScrollingParent
import android.support.v4.view.NestedScrollingParentHelper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.uoko.nestedscorll.R
import com.uoko.nestedscorll.ScreenUtils
import kotlinx.android.synthetic.main.bar_top_layout.view.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.function.Consumer


/**
 * 作者: xwb
 * 描述: 统一的title布局,可以配合实现了NestedScrollingChild的类联合滚动
 */

class UKTopBarLayout : FrameLayout, NestedScrollingParent, ViewTreeObserver.OnPreDrawListener {

    var isChildScroll:Boolean = true


    var mSmallHei:Int = 0
    var mDefTitleLayoutOffest  = 0
    var mTestHeight = 0
    var mTwoOffest = 0

    override fun onPreDraw(): Boolean {
        mTestHeight = mTestHeightLayout.height
        mTitleLayoutOffest = (mOpenHeight - mCloseHeight) + (mCloseHeight - mTestHeight) / 2
        mTwoOffest = mTitleLayoutOffest
        mSmallHei = mTestHeightLayout.findViewById<View>(R.id.tv_test_small).height
        mTitleLayoutOffest += mSmallHei/2
        mBigSectionVa = (mChildTransY/mSectionBig).toInt()
        mSmallSectionVa = (mChildTransY/mSectionSmall).toInt()
        mTestHeightLayout.viewTreeObserver.removeOnPreDrawListener(this)
        return true
    }

    var mChild: View? = null//需要偏移的孩子
    var mChildOffest = 0 //孩子偏移的距离
    var mChildTransY  = 0
    var mBigSectionVa = 0
    var mSmallSectionVa = 0

    var isFling:Boolean = true

    override fun onFinishInflate() {
        super.onFinishInflate()

//        mChild = getChildAt(1)


//        if (mChild != null) {
//            mChild!!.translationY = mOpenHeight.toFloat()
//        }

    }

    override fun onStopNestedScroll(target: View) {
        mNestedScrollingParentHelper.onStopNestedScroll(target)
    }


    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {

        return isFling //如果已经滚动完成就释放

    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {

        return true
    }

    val balckIm: LinearLayout
    var mTopBar: View
    var mTitleLayout: ConstraintLayout
    val mNestedScrollingParentHelper: NestedScrollingParentHelper
    val mOffsetVa: Float = 0.8f //弹性值
    var mOpenHeight: Int //打开的高度
    val mCloseHeight: Int //关闭的高度
    val mLeftMargin: Int
    val mLeftOffest: Int
    val TAG: String = "NewUKTopBarLayout"
    val mTestHeightLayout:View


    @JvmOverloads
    constructor(context: Context, attributes: AttributeSet? = null, defStyleAattr: Int = 0) : super(context, attributes, defStyleAattr) {
        setBarLayoutBackGround(ContextCompat.getColor(context,android.R.color.white))
        fitsSystemWindows = true
        mNestedScrollingParentHelper = NestedScrollingParentHelper(this)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mTopBar = LayoutInflater.from(context).inflate(R.layout.bar_top_layout, this, false)
        balckIm = mTopBar.findViewById(R.id.uk_black_im)
        mTitleLayout = mTopBar.findViewById(R.id.uk_title_layouts)
        mTestHeightLayout = mTopBar.findViewById(R.id.ll_test_height)
        mTestHeightLayout.viewTreeObserver.addOnPreDrawListener(this)
        mOpenHeight = resources.getDimensionPixelSize(R.dimen.uk_top_bar_open_height)
        mCloseHeight = resources.getDimensionPixelSize(R.dimen.uk_top_bar_close_height)
        mLeftMargin = resources.getDimensionPixelSize(R.dimen.dimen_dp_24)
        mLeftOffest = (mLeftMargin * 1.5).toInt()
        mChildOffest = mCloseHeight
        mChildTransY = mOpenHeight - mCloseHeight
        addView(mTopBar)
    }


    fun installItem(itemName:String="",click: Consumer<Any>){
        tv_operation_btn.text = itemName
    }
    fun installItem(itemNameid:Int,click:Consumer<Any>){
        tv_operation_btn.text = context.getString(itemNameid)
    }

    fun setBigTitleImClick(click:Consumer<Any>){
        im_big_click.visibility = View.VISIBLE

    }


    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {



        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes)
    }


    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
    }


    //标题往上最大偏移距离
    var mTitleLayoutOffest = 0

    var mBigTitleMaxSize:Float = 28.0f//大标题默认大小
    var mBigTitleSmallSize:Float = 20.0f
    var mSectionBig = mBigTitleMaxSize - mBigTitleSmallSize


    var mSmallTitleMaxSize:Float = 18.0f//小标题默认大小
    var mSmallTitleSmallSize:Float = 14.0f
    var mSectionSmall = mSmallTitleMaxSize - mSmallTitleSmallSize




    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {

        if(!isChildScroll)
            return

       //var childParas:FrameLayout.LayoutParams = mChild?.layoutParams as LayoutParams



        if (dy > 0) {//向上

            //child b------------




            var chlidTrans = mChild?.translationY

            var realChildVa: Int


            if ((chlidTrans!! - dy) <= mChildOffest) {
                realChildVa = mChildOffest
            } else {
                realChildVa = (chlidTrans - dy).toInt()

            }




            var trsVa =  (mOpenHeight - realChildVa).toDouble()


            var dvBig = BigDecimal(trsVa).divide(BigDecimal(mBigSectionVa.toDouble()), 2, RoundingMode.HALF_UP)
            uk_title_big.textSize = mBigTitleMaxSize - dvBig.toFloat()

            var dvSmall = BigDecimal(trsVa).divide(BigDecimal(mSmallSectionVa.toDouble()), 2, RoundingMode.HALF_UP)

            uk_title_small.textSize = mSmallTitleMaxSize - dvSmall.toFloat()

            mChild!!.translationY = realChildVa.toFloat()
//            LogUtils.e(TAG,""+dy+" "+dvBig.toFloat())
//
////            mChild!!.setPadding(0,realChildVa,0,0)
//            mChild!!.scrollTo(0,-realChildVa)


            //child e------------


            //标题向右 b----------------

            var chlidRightVa = ll_title_layout.translationX
            var realRightVa: Int

            if ((chlidRightVa + dy) >= mLeftOffest) {
                realRightVa = mLeftOffest
            } else {
                realRightVa = (chlidRightVa + dy * mOffsetVa).toInt()
            }

            ll_title_layout.translationX = realRightVa.toFloat()


            //标题向右 e----------------


            //标题向上-------------b

            var tlTranY = Math.abs(mTitleLayout.translationY)

            var realTranVa: Int

            if ((tlTranY + dy) >= mTitleLayoutOffest) {
                realTranVa = mTitleLayoutOffest
            } else {
                realTranVa = (tlTranY + dy * 1.2).toInt()
            }
            mTitleLayout.translationY = (-realTranVa).toFloat()

            //标题向上-------------e


            if(mChild?.translationY!!.toFloat()==mChildOffest.toFloat()){
                isFling = false
            }else{
                isFling = true
                consumed[1] = dy
            }



        } else {//向下


            //child b------------

            var chlidTrans = mChild?.translationY
//            var chlidTrans = Math.abs(mChild?.scrollY!!)

            var realChildVa: Int


            if ((chlidTrans!! - dy) >= mOpenHeight) {
                realChildVa = mOpenHeight
            } else {
                realChildVa = (chlidTrans - dy).toInt()
            }

            var trsVa =  (mOpenHeight - realChildVa).toDouble()



            var dvBig = BigDecimal(trsVa).divide(BigDecimal(mBigSectionVa.toDouble()), 2, RoundingMode.HALF_UP)
            uk_title_big.textSize = mBigTitleSmallSize + (mSectionBig-dvBig.toFloat())


            var dvSmall = BigDecimal(trsVa).divide(BigDecimal(mSmallSectionVa.toDouble()), 2, RoundingMode.HALF_UP)
            uk_title_small.textSize = mSmallTitleSmallSize + (mSectionSmall-dvSmall.toFloat())


            mChild!!.translationY = realChildVa.toFloat()

//            mChild!!.setPadding(0,realChildVa,0,0)
//            mChild!!.scrollTo(0,realChildVa)


            //child e------------


            //标题向左 b----------------

            var chlidRightVa = ll_title_layout.translationX
            var realRightVa: Int

            if ((chlidRightVa - Math.abs(dy)) <=0) {
                realRightVa = 0
            } else {
                realRightVa = (chlidRightVa - Math.abs(dy) * mOffsetVa).toInt()
            }

            ll_title_layout.translationX = realRightVa.toFloat()


            //标题向左 e----------------


            //标题向下-------------b

            var tlTranY = Math.abs(mTitleLayout.translationY)

            var realTranVa: Int

            if ((tlTranY - Math.abs(dy)) <= 0) {
                realTranVa = 0
            } else {
                realTranVa = (tlTranY - Math.abs(dy) * 1.2).toInt()
            }
            mTitleLayout.translationY = (-realTranVa).toFloat()


            //标题向下-------------e


            if(mChild?.translationY?.toFloat()==mOpenHeight.toFloat()){
                isFling = false
            }else{
                isFling = true
                consumed[1] = dy
            }



        }
    }


    override fun getNestedScrollAxes(): Int {
        return mNestedScrollingParentHelper.nestedScrollAxes
    }




    fun setUkBigTitle(txt: String) {

        if(txt.length>7){

            uk_title_big.text =  txt.substring(0,7)+"..."

        }else{
            uk_title_big.text = txt
        }





    }

    fun setUkBigTitle(txtId: Int) {
        var txt = context.getString(txtId)
        if(txt.length>7){
            uk_title_big.text =  txt.substring(0,7)+"..."
        }else{
            uk_title_big.text = txt
        }
    }

    var isChange:Boolean = true


    fun setUkSmallTitle(txt: String) {
        uk_title_small.text = txt
        uk_title_small.visibility = View.VISIBLE

        if(isChange) {

            isChange = false

            if (isChildScroll) {
                mChild?.translationY = mChild?.translationY!! + ScreenUtils.dp2px(context, 20f).toFloat()
            } else {
                (mChild?.parent as ViewGroup).setPadding(0, (mChild?.parent as ViewGroup).paddingTop - ScreenUtils.dp2px(context, 20f), 0, 0)
            }


            var childParas: FrameLayout.LayoutParams = mTopBar.layoutParams as LayoutParams
            childParas.height = mOpenHeight + ScreenUtils.dp2px(context, 20f)
            mTopBar.layoutParams = childParas
            postDelayed(Runnable {
                mOpenHeight = mOpenHeight + ScreenUtils.dp2px(context, 20f)

                mChildTransY = mOpenHeight - mCloseHeight

                mTitleLayoutOffest = (mOpenHeight - mCloseHeight) + (mCloseHeight - mTestHeightLayout.height) / 2
                mBigSectionVa = (mChildTransY / mSectionBig).toInt()
                mSmallSectionVa = (mChildTransY / mSectionSmall).toInt()



            }, 500)
        }


    }

    fun setUkSmallTitle(txtId: Int) {
        uk_title_small.text = context.getString(txtId)
        uk_title_small.visibility = View.VISIBLE

        if(isChange) {
            isChange = false
            if (isChildScroll) {
                mChild?.translationY = mChild?.translationY!! + ScreenUtils.dp2px(context, 20f).toFloat()
            } else {
                (mChild?.parent as ViewGroup).setPadding(0, (mChild?.parent as ViewGroup).paddingTop - ScreenUtils.dp2px(context, 20f), 0, 0)
            }


            var childParas: FrameLayout.LayoutParams = mTopBar.layoutParams as LayoutParams
            childParas.height = mOpenHeight + ScreenUtils.dp2px(context, 20f)
            mTopBar.layoutParams = childParas
            postDelayed(Runnable {
                mOpenHeight = mOpenHeight + ScreenUtils.dp2px(context, 20f)

                mChildTransY = mOpenHeight - mCloseHeight

                mTitleLayoutOffest = (mOpenHeight - mCloseHeight) + (mCloseHeight - mTestHeightLayout.height) / 2
                mBigSectionVa = (mChildTransY / mSectionBig).toInt()
                mSmallSectionVa = (mChildTransY / mSectionSmall).toInt()


            }, 500)

        }

    }

    fun setBarLayoutBackGround(color :Int){
        setBackgroundColor(color)
    }

    fun setOperTxtColor(color :Int){
        tv_operation_btn.setTextColor(color)
        tv_operation_btn.paint.typeface = Typeface.DEFAULT_BOLD
    }

    fun setWhiteStyle(){
//        uk_tl_back_im.setImageResource(R.mipmap.ic_topbar_white_ac)
//        uk_title_big.setTextColor(ContextCompat.getColor(context,R.color.uk_white))
//        uk_title_small.setTextColor(ContextCompat.getColor(context,R.color.uk_white))
//        tv_operation_btn.setTextColor(ContextCompat.getColor(context,R.color.uk_white))
    }



}
