package fr.enssat.babelblock.sueur_wieczorek

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.enssat.babelblock.sueur_wieczorek.screens.blocktranslator.BlockTranslatorFragment
import fr.enssat.babelblock.sueur_wieczorek.screens.translator.TranslatorFragment

class TabsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private var numberOfTabs: Int) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                // # Translator Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Music Fragment")
                val musicFragment = TranslatorFragment()
                musicFragment.arguments = bundle
                return musicFragment
            }
            1 -> {
                // # Blocks Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Movies Fragment")
                val moviesFragment = BlockTranslatorFragment()
                moviesFragment.arguments = bundle
                return moviesFragment
            }
            else -> return TranslatorFragment()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}