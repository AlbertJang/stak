package `fun`.stak.di

import `fun`.stak.viewmodels.CompanyViewModel
import `fun`.stak.viewmodels.DiaryPayViewModel
import `fun`.stak.viewmodels.DiaryViewModel
import `fun`.stak.viewmodels.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { UserViewModel(get()) }
    viewModel { DiaryViewModel(get()) }
    viewModel { CompanyViewModel(get()) }
    viewModel { DiaryPayViewModel(get()) }
}