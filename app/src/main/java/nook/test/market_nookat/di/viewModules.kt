package nook.test.market_nookat.di

import nook.test.market_nookat.ui.activity.MainViewModel
import nook.test.market_nookat.ui.fragment.add.AddViewModel
import nook.test.market_nookat.ui.fragment.ads.AdsViewModel
import nook.test.market_nookat.ui.fragment.edit.EditViewModel
import nook.test.market_nookat.ui.fragment.favorite.FavoriteViewModel
import nook.test.market_nookat.ui.fragment.favorite.ads.FavAdsViewModel
import nook.test.market_nookat.ui.fragment.favorite.ads.photo.FavPhotoViewModel
import nook.test.market_nookat.ui.fragment.filter.FilterViewModel
import nook.test.market_nookat.ui.fragment.home.HomeViewModel
import nook.test.market_nookat.ui.fragment.photo.PhotoViewModel
import nook.test.market_nookat.ui.fragment.profile.ProfileViewModel
import nook.test.market_nookat.ui.fragment.profile.active.ActiveViewModel
import nook.test.market_nookat.ui.fragment.profile.active.ads_active.AdsActiveViewModel
import nook.test.market_nookat.ui.fragment.profile.active.ads_active.photo_ads.PhotoActiveViewModel
import nook.test.market_nookat.ui.fragment.profile.active.edit.EditActiveViewModel
import nook.test.market_nookat.ui.fragment.profile.no_active.ads_two.AdsTwoViewModel
import nook.test.market_nookat.ui.fragment.profile.no_active.ads_two.photo_ads.PhotoAdsViewModel
import nook.test.market_nookat.ui.fragment.profile.no_active.NoActiveViewModel
import nook.test.market_nookat.ui.fragment.settings.SettingsViewModel
import nook.test.market_nookat.ui.fragment.settings.about.AboutViewModel
import nook.test.market_nookat.ui.fragment.settings.ads.AdsSerViewModel
import nook.test.market_nookat.ui.fragment.settings.ads.notifi.AdsFireViewModel
import nook.test.market_nookat.ui.fragment.settings.ads.photo.PhotoNotifiViewModel
import nook.test.market_nookat.ui.fragment.settings.instructions.InstructionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModules = module {
    viewModel {AddViewModel(get())}
    viewModel {EditViewModel(get()) }
    viewModel {EditActiveViewModel(get()) }
    viewModel {AdsViewModel(get()) }
    viewModel {AdsActiveViewModel(get()) }
    viewModel {FavoriteViewModel(get()) }
    viewModel {FavAdsViewModel(get()) }
    viewModel {FavPhotoViewModel(get()) }
    viewModel {FilterViewModel(get()) }
    viewModel {HomeViewModel(get()) }
    viewModel {PhotoViewModel(get()) }
    viewModel {PhotoActiveViewModel(get()) }
    viewModel {ProfileViewModel(get()) }
    viewModel {ActiveViewModel(get()) }
    viewModel {NoActiveViewModel(get()) }
    viewModel { AdsTwoViewModel(get()) }
    viewModel { AdsFireViewModel(get()) }
    viewModel { PhotoNotifiViewModel(get()) }
    viewModel { PhotoAdsViewModel(get()) }
    viewModel {SettingsViewModel(get()) }
    viewModel {InstructionsViewModel(get()) }
    viewModel {AboutViewModel(get()) }
    viewModel {MainViewModel(get()) }
    viewModel { AdsSerViewModel(get()) }
}