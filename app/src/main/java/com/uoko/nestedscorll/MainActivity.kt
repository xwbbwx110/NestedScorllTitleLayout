package com.uoko.nestedscorll

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.test_scroll_layouts.*
import java.util.function.Consumer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_scroll_layouts)
        test_pager.translationY = uk_title.mOpenHeight.toFloat()
        mFr = listOf(testFragment(),testFragmentss())
        test_pager.adapter = testAdapter()
        uk_title.mChild = test_pager
        uk_title.isChildScroll = true
        uk_title.setUkBigTitle("大标题")
        uk_title.setUkSmallTitle("这里是小标题")
//        uk_title.setWhiteStyle()
//        uk_title.installItem("操作", Consumer {
//
//        })

        uk_title.setBigTitleImClick( Consumer {

        })





    }
    lateinit var mFr:List<Fragment>


    inner class testAdapter: FragmentPagerAdapter {

        constructor() : super(supportFragmentManager)

        override fun getItem(position: Int): Fragment {
            return  mFr[position]

        }

        override fun getCount(): Int {
            return 2
        }

    }

}
