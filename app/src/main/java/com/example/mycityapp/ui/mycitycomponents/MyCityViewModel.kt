package com.example.mycityapp.ui.mycitycomponents

import androidx.lifecycle.ViewModel
import com.example.mycityapp.data.Datasource
import com.example.mycityapp.model.Category
import com.example.mycityapp.model.Recommendation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MyCityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState: StateFlow<MyCityUiState> = _uiState

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        _uiState.value = MyCityUiState(
            categories = Datasource.listOfCategories
        )
    }

    fun updateCurrentCategory(category: Category) {
        _uiState.update {
            it.copy(
                currentCategory = category
            )
        }
    }


    fun updateCurrentRecommendation(place: Recommendation?) {
        _uiState.update {
            it.copy(
                currentRecommendation = place
            )
        }
    }

    fun getNextRecommendation(): Recommendation? {
        return if (_uiState.value.currentRecommendation != null) {
            getNext(_uiState.value.currentCategory!!.list, _uiState.value.currentRecommendation)
        } else
            null
    }

    fun getPreviousRecommendation(): Recommendation? {
        return if (_uiState.value.currentRecommendation != null) {
            getPrevious(_uiState.value.currentCategory!!.list, _uiState.value.currentRecommendation)
        } else
            null
    }

    private fun <T> getNext(list: List<T>, current: T): T {
        val currentIndex = list.indexOf(current)
        return if (currentIndex < list.size - 1) {
            list[currentIndex + 1]
        } else {
            list[0]
        }
    }

    private fun <T> getPrevious(list: List<T>, current: T): T {
        val currentIndex = list.indexOf(current)
        return if (currentIndex > 0) {
            list[currentIndex - 1]
        } else {
            list[list.size - 1]
        }
    }
}