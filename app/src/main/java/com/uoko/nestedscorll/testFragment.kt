package com.uoko.nestedscorll

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 描述:
 */
class testFragment : Fragment() {

    var testList = listOf<String>("","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","")




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = LayoutInflater.from(activity).inflate(R.layout.test_fr_layout,container,false)

//      var rl =   view.findViewById<RecyclerView>(R.id.test_fr_rl)
//
//
//
//        rl.layoutManager = LinearLayoutManager(activity)
//
//        rl.adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.test_rl_item,testList){
//            override fun convert(helper: BaseViewHolder?, item: String?) {
//
//                helper?.setText(R.id.content_txt,""+helper?.layoutPosition)
//
//
//
//
//            }
//
//        }



        return view
    }



}