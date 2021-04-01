package fpt.adtrue.horoscope.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fpt.adtrue.horoscope.application.App
import fpt.adtrue.horoscope.databinding.SignBinding

class SignFragment(val position: Int) : Fragment() {

    private lateinit var binding: SignBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = SignBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gridImage.setImageResource(App.getZodiac()[position].image)
        binding.gridText.text = App.getZodiac()[position].name

    }

}