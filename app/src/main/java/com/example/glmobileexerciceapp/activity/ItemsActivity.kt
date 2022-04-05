package com.example.glmobileexerciceapp.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entitie.ItemEntity
import com.example.glmobileexerciceapp.R
import com.example.glmobileexerciceapp.adapter.ItemsAdapter
import com.example.glmobileexerciceapp.adapter.OnItemClicked
import com.example.glmobileexerciceapp.databinding.ActivityItemsBinding
import com.example.glmobileexerciceapp.fragment.ItemDetailDialogFragment
import com.example.glmobileexerciceapp.util.Event
import com.example.glmobileexerciceapp.viewmodel.ItemsData
import com.example.glmobileexerciceapp.viewmodel.ItemsStatus
import com.example.glmobileexerciceapp.viewmodel.ItemsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemsActivity : AppCompatActivity(), OnItemClicked {

    private lateinit var binding: ActivityItemsBinding
    private val itemsViewModel by viewModel<ItemsViewModel>()

    private val itemsAdapter = ItemsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemsViewModel.itemsLiveData.observe(::getLifecycle, ::updateUI)
        itemsViewModel.fetchItems()
    }

    private fun updateUI(data: Event<ItemsData>) {
        val mainState = data.getContentIfNotHandled()
        when (mainState?.status) {
            ItemsStatus.LOADING -> {
                binding.itemsActivityLoader.visibility = View.VISIBLE
            }
            ItemsStatus.SUCCESS_DATA -> {
                binding.itemsActivityLoader.visibility = View.GONE
                showItemsData(data.peekContent().data as List<ItemEntity>)
            }
            ItemsStatus.ERROR -> {
                binding.itemsActivityLoader.visibility = View.GONE
                showErrorModal(data.peekContent().error?.message.orEmpty())
            }
            ItemsStatus.OPEN_ITEM_DETAIL -> {
                openItemsDetail(data.peekContent().data as ItemEntity)
            }
        }
    }

    private fun showItemsData(data: List<ItemEntity>) {

        binding.itemsActivityLoader.visibility = View.GONE
        itemsAdapter.submitList(data)
        binding.itemsActivityRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.itemsActivityRecyclerView.adapter = itemsAdapter
    }

    private fun openItemsDetail(item: ItemEntity) {
        val dialog = ItemDetailDialogFragment.newInstance(item)
        dialog.show(supportFragmentManager, "TAG")
    }

    private fun showErrorModal(message: String) {

        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(getString(R.string.alert_dialog_positive_button_text)) { _, _ ->
                itemsViewModel.fetchItems()

            }
            .setNegativeButton(getString(R.string.alert_dialog_negative_button_text)) { _, _ ->

                this.finish()
            }
            .show()
    }

    override fun onItemClicked(item: ItemEntity) {
        itemsViewModel.onItemClicked(item)
    }
}

