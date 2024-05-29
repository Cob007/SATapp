package com.nycschools.app.ui.schInNyc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nycschools.app.R
import com.nycschools.app.databinding.ActivitySchInNycBinding
import com.nycschools.app.databinding.ItemViewBinding
import com.nycschools.app.model.ApiResponse
import com.nycschools.app.ui.satNyc.SatNycActivity
import com.nycschools.app.util.DiffUtils

class SchInNycActivity : AppCompatActivity(), SchInNycAdapter.ItemClickLister {
    private lateinit var binding : ActivitySchInNycBinding
    private lateinit var adapter : SchInNycAdapter

    private val viewModel: SchInNycViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_sch_in_nyc)
/*
        viewmodelproviders is deprecated
        val viewModel = ViewModelProviders.of(this).get(SchInNycViewModel::class.java)
*/
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.viewModel?.init()
        setView()
        observer()
    }

    private fun observer() {
        binding.viewModel?.list?.observe(this) {
            //update Adapter here
            adapter.updateList(it)
        }
    }

    private fun setView() {
        adapter = SchInNycAdapter(this)
        val layoutManager = LinearLayoutManager(this)
        binding.rv.layoutManager = layoutManager
        binding.rv.adapter = adapter
    }

    override fun onClickItem(dbn: String?) {
        //Toast.makeText(this, dbn!!, Toast.LENGTH_SHORT).show()
        startActivity(SatNycActivity.newIntent(this, dbn!!))
    }
}

class SchInNycAdapter constructor(private val itemClickLister: ItemClickLister) : RecyclerView.Adapter<SchInNycAdapter.ViewHolder>() {
    class ViewHolder (val itemViewBinding: ItemViewBinding): RecyclerView.ViewHolder(itemViewBinding.root)

    interface ItemClickLister {
        fun onClickItem(dbn : String?)
    }

    private var list : List<ApiResponse> = emptyList()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemViewBinding.model = list[position]
        holder.itemViewBinding.layout.setOnClickListener {
            itemClickLister.onClickItem(list[position].dbn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ItemViewBinding.inflate(inflater, parent, false)
        return ViewHolder(dataBinding)
    }

    fun updateList(it: List<ApiResponse>) {
        val diffCallback = DiffUtils(this.list, it)
        val diffCourses = DiffUtil.calculateDiff(diffCallback)
        this.list = it
        diffCourses.dispatchUpdatesTo(this)
    }
}
