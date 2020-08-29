package com.ms.app.ui.fragment.addressbook

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.ms.app.R;

import com.ms.app.addressBookContent
import com.ms.app.addressBookIndex
import com.ms.app.ui.base.BaseFragment
import com.ms.app.ui.base.simpleController
import kotlinx.android.synthetic.main.fragment_address_book.*

class AddressBookFragment : BaseFragment() {

    private val addressBookFragmentViewModel: AddressBookFragmentViewModel by fragmentViewModel()

    override fun epoxyController() = simpleController {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_address_book
    }

    override fun invalidate() {}

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        epoxyRecyclerViewAddressBookContent.withModels {


            for (i in 0..10) {

                addressBookContent {
                    id(View.generateViewId())
                    name("张三")
                }

            }

        }
        epoxyRecyclerViewAddressBookIndex.withModels {

            for (i in 'A'..'Z') {
                addressBookIndex {
                    id(View.generateViewId())
                    text(i + "")
                }
            }
        }


    }
}