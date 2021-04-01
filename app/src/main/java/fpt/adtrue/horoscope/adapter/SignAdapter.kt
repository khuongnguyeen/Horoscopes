package fpt.adtrue.horoscope.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import fpt.adtrue.horoscope.R
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.fragment.SignFragment

class SignAdapter (context:Context) : BaseAdapter(){
    var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null){
            view = layoutInflater.inflate(R.layout.sign,parent,false)
        }

        var img = view?.findViewById<ImageView>(R.id.grid_image)
        var tv = view?.findViewById<TextView>(R.id.grid_text)

        img?.setImageResource(App.getZodiac()[position].image)
        tv?.text = App.getZodiac()[position].name


        return view!!
    }

    override fun getItem(position: Int): Any {
        return App.getZodiac()[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
       return App.getZodiac().size
    }


    //    override fun getCount() = 12
//
//    override fun getItem(position: Int): Fragment {
//        return SignFragment(position)
//
//    }



}