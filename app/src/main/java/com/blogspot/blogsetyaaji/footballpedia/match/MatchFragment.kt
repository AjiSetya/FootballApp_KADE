package com.blogspot.blogsetyaaji.footballpedia.match

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.*
import com.blogspot.blogsetyaaji.footballpedia.R
import com.blogspot.blogsetyaaji.footballpedia.adapter.MyTabAdapter
import com.blogspot.blogsetyaaji.footballpedia.match.lastmatch.LastMatchFragment
import com.blogspot.blogsetyaaji.footballpedia.match.nextmatch.NextMatchFragment
import com.blogspot.blogsetyaaji.footballpedia.match.searchmatch.SearchMatchActivity
import kotlinx.android.synthetic.main.fragment_match.*
import org.jetbrains.anko.support.v4.startActivity

class MatchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabAdapter = MyTabAdapter(activity?.supportFragmentManager)
        tabAdapter.addFragment(LastMatchFragment(), getString(R.string.last_match))
        tabAdapter.addFragment(NextMatchFragment(), getString(R.string.next_match))
        pagerMatch.adapter = tabAdapter
        tabMatch.setupWithViewPager(pagerMatch)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search_match, menu)

        val searchView = menu?.findItem(R.id.search_match)?.actionView as SearchView?
        searchView?.queryHint = getString(R.string.search_matches)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                this@MatchFragment.startActivity<SearchMatchActivity>("query" to p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }
}
