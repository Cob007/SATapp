package com.nycschools.app.ui.satNyc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.nycschools.app.R
import com.nycschools.app.databinding.ActivitySatNycBinding

class SatNycActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySatNycBinding

    private val viewModel : SatNycViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sat_nyc )

        //val viewModel = ViewModelProvider(this)[SatNycViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val userDbn = intent.getStringExtra(INTENT_DBN)
            ?: throw IllegalStateException("field $INTENT_DBN missing in Intent")

        binding.viewModel?.getByDbn(userDbn)

    }

    companion object{
        private var INTENT_DBN = "dbn"
        fun newIntent(context: Context, dbn: String): Intent {
            val intent = Intent(context, SatNycActivity::class.java)
            intent.putExtra(INTENT_DBN, dbn)
            return intent
        }
    }
}