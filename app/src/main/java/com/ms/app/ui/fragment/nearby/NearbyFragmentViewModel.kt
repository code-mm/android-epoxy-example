package com.ms.app.ui.fragment.nearby

import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.ms.app.app.App
import com.ms.app.ui.base.MvRxViewModel

class NearbyFragmentViewModel(
        initialState: NearbyState,
        private val nearbyRepository: NearbyRepository
) : MvRxViewModel<NearbyState>(initialState) {
    init {
        setState {
            copy(model = Loading())
        }
    }

    companion object : MvRxViewModelFactory<NearbyFragmentViewModel, NearbyState> {
        override fun create(
                viewModelContext: ViewModelContext,
                state: NearbyState
        ): NearbyFragmentViewModel? {
            val repository =
                    viewModelContext.app<App>().baseRepository.nearbyRepository
            return NearbyFragmentViewModel(
                    state,
                    repository
            )
        }
    }
}

