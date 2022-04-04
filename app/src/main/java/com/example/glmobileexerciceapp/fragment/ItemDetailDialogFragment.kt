package com.example.glmobileexerciceapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.domain.entitie.ItemEntity
import com.example.glmobileexerciceapp.databinding.FragmentItemDetailBinding
import com.example.glmobileexerciceapp.extension.getImageByUrl
import com.example.glmobileexerciceapp.util.Event
import com.example.glmobileexerciceapp.viewmodel.ItemDetailData
import com.example.glmobileexerciceapp.viewmodel.ItemDetailStatus
import com.example.glmobileexerciceapp.viewmodel.ItemDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemDetailDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentItemDetailBinding
    private val itemDetailViewModel by viewModel<ItemDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentItemDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemDetailViewModel.itemDetailLiveData.observe(::getLifecycle, ::updateUI)
        itemDetailViewModel.getItemInformation(arguments?.getSerializable(EXTRA_ITEM) as ItemEntity)
    }

    private fun updateUI(data: Event<ItemDetailData>) {
        val mainState = data.getContentIfNotHandled()
        when (mainState?.status) {
            ItemDetailStatus.CHARGE_ITEM_INFORMATION -> {
                showItemDetail(data.peekContent().data as ItemEntity)
            }
        }
    }

    private fun showItemDetail(itemEntity: ItemEntity) {

        binding.apply {
            this.itemDetailName.text = itemEntity.title
            this.itemDetailDescription.text = itemEntity.description

            this.itemDetailImage.getImageByUrl(itemEntity.image)
        }
    }

    companion object {
        fun newInstance(item: ItemEntity): DialogFragment {
            val args = Bundle()
            args.putSerializable(EXTRA_ITEM, item)
            val fragment = ItemDetailDialogFragment()
            fragment.arguments = args
            return fragment
        }

        private const val EXTRA_ITEM: String = "EXTRA_ITEM"
    }
}