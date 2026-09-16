package com.dwngkhoi.iohksu.di

import coil.ImageLoader
import com.dwngkhoi.iohksu.BuildConfig
import com.dwngkhoi.iohksu.data.AppSettingsRepository
import com.dwngkhoi.iohksu.data.application.ApplicationControlRepository
import com.dwngkhoi.iohksu.data.application.DynamicManagerRepository
import com.dwngkhoi.iohksu.data.download.DownloadRepository
import com.dwngkhoi.iohksu.data.file.ModuleFileRepository
import com.dwngkhoi.iohksu.data.flash.FlashRepository
import com.dwngkhoi.iohksu.data.kernel.KernelRepository
import com.dwngkhoi.iohksu.data.kernel.UmountRepository
import com.dwngkhoi.iohksu.data.logging.BugreportRepository
import com.dwngkhoi.iohksu.data.logging.SulogRepository
import com.dwngkhoi.iohksu.data.module.ModuleActionRepository
import com.dwngkhoi.iohksu.data.module.ModuleCatalogRepository
import com.dwngkhoi.iohksu.data.module.ModulePreferencesRepository
import com.dwngkhoi.iohksu.data.module.ModuleRepository
import com.dwngkhoi.iohksu.data.network.NetworkRequestRepository
import com.dwngkhoi.iohksu.data.network.NetworkStatusRepository
import com.dwngkhoi.iohksu.data.network.WebResourceRepository
import com.dwngkhoi.iohksu.data.packageinfo.AppIconDataSource
import com.dwngkhoi.iohksu.data.packageinfo.InstalledPackageCache
import com.dwngkhoi.iohksu.data.packageinfo.InstalledPackageRepository
import com.dwngkhoi.iohksu.data.packageinfo.RootServiceRepository
import com.dwngkhoi.iohksu.data.packageinfo.SuperUserRepository
import com.dwngkhoi.iohksu.data.profile.ProfileRepository
import com.dwngkhoi.iohksu.data.profile.ProfileTemplateRepository
import com.dwngkhoi.iohksu.data.settings.LocaleHelper
import com.dwngkhoi.iohksu.data.settings.LocaleRepository
import com.dwngkhoi.iohksu.data.settings.SettingsPlatformRepository
import com.dwngkhoi.iohksu.data.shell.KsuCliRepository
import com.dwngkhoi.iohksu.data.shell.ShortcutRepository
import com.dwngkhoi.iohksu.data.startup.ApplicationInitializationRepository
import com.dwngkhoi.iohksu.data.startup.StartupRepository
import com.dwngkhoi.iohksu.data.susfs.SuSFSConfigHelper
import com.dwngkhoi.iohksu.data.susfs.SuSFSRepository
import com.dwngkhoi.iohksu.data.system.HomeRuntimeRepository
import com.dwngkhoi.iohksu.data.system.HomeStateRepository
import com.dwngkhoi.iohksu.data.text.HanziToPinyin
import com.dwngkhoi.iohksu.data.theme.MonetCompatColorSource
import com.dwngkhoi.iohksu.data.theme.ThemeRepository
import com.dwngkhoi.iohksu.data.update.ManagerUpdateRepository
import com.dwngkhoi.iohksu.data.webui.WebUiRepository
import com.dwngkhoi.iohksu.domain.text.TextTransliterator
import com.dwngkhoi.iohksu.domain.usecase.AddUmountPathUseCase
import com.dwngkhoi.iohksu.domain.usecase.ApplyLanguageUseCase
import com.dwngkhoi.iohksu.domain.usecase.BackupAllowlistUseCase
import com.dwngkhoi.iohksu.domain.usecase.CalculateInstalledModuleSizeUseCase
import com.dwngkhoi.iohksu.domain.usecase.CheckFlashModuleMountUseCase
import com.dwngkhoi.iohksu.domain.usecase.CheckManagerUpdateUseCase
import com.dwngkhoi.iohksu.domain.usecase.CleanSulogUseCase
import com.dwngkhoi.iohksu.domain.usecase.ClearDynamicManagerUseCase
import com.dwngkhoi.iohksu.domain.usecase.ConfigureSuLogUseCase
import com.dwngkhoi.iohksu.domain.usecase.ControlAppUseCase
import com.dwngkhoi.iohksu.domain.usecase.DeleteProfileTemplateUseCase
import com.dwngkhoi.iohksu.domain.usecase.EnableSulogUseCase
import com.dwngkhoi.iohksu.domain.usecase.EnqueueDownloadUseCase
import com.dwngkhoi.iohksu.domain.usecase.EnqueueManagerUpdateUseCase
import com.dwngkhoi.iohksu.domain.usecase.EnsureManagerInstalledUseCase
import com.dwngkhoi.iohksu.domain.usecase.ExecuteFlashOperationUseCase
import com.dwngkhoi.iohksu.domain.usecase.ExecuteModuleActionUseCase
import com.dwngkhoi.iohksu.domain.usecase.ExportProfileTemplatesUseCase
import com.dwngkhoi.iohksu.domain.usecase.ExtractModuleIdUseCase
import com.dwngkhoi.iohksu.domain.usecase.ExtractModuleNameUseCase
import com.dwngkhoi.iohksu.domain.usecase.FetchRemoteTextUseCase
import com.dwngkhoi.iohksu.domain.usecase.GenerateBugreportUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetAppProfileUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetAppSepolicyUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetBooleanPreferenceUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetCatalogModuleUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetDefaultUmountModulesUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetHomeBasicInfoUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetHomeModuleOverviewUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetHomeSuperuserCountUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetInstallEnvironmentUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetKernelFeatureSettingsUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetKernelStatusUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetManagerRuntimeInfoUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetPlatformFeatureStatusUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetProfileTemplateUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetStringPreferenceUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetStringSetPreferenceUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetSuSFSStatusUseCase
import com.dwngkhoi.iohksu.domain.usecase.GetSuperUserAppGroupUseCase
import com.dwngkhoi.iohksu.domain.usecase.ImportAllowlistUseCase
import com.dwngkhoi.iohksu.domain.usecase.ImportProfileTemplatesUseCase
import com.dwngkhoi.iohksu.domain.usecase.InitializeApplicationUseCase
import com.dwngkhoi.iohksu.domain.usecase.IsLateLoadModeUseCase
import com.dwngkhoi.iohksu.domain.usecase.IsModuleUriAccessibleUseCase
import com.dwngkhoi.iohksu.domain.usecase.IsNetworkAvailableUseCase
import com.dwngkhoi.iohksu.domain.usecase.IsSystemLanguageSettingsUseCase
import com.dwngkhoi.iohksu.domain.usecase.LaunchSystemLanguageSettingsUseCase
import com.dwngkhoi.iohksu.domain.usecase.LoadSettingsPlatformUseCase
import com.dwngkhoi.iohksu.domain.usecase.ObserveCatalogModulesUseCase
import com.dwngkhoi.iohksu.domain.usecase.ObserveDownloadUseCase
import com.dwngkhoi.iohksu.domain.usecase.ObserveDynamicManagerStateUseCase
import com.dwngkhoi.iohksu.domain.usecase.ObserveInstalledModulesUseCase
import com.dwngkhoi.iohksu.domain.usecase.ObserveKernelFlashUseCase
import com.dwngkhoi.iohksu.domain.usecase.ObserveModuleCatalogOfflineUseCase
import com.dwngkhoi.iohksu.domain.usecase.ObserveModuleCatalogRefreshingUseCase
import com.dwngkhoi.iohksu.domain.usecase.ObserveProfileTemplateOfflineUseCase
import com.dwngkhoi.iohksu.domain.usecase.ObserveProfileTemplateRefreshingUseCase
import com.dwngkhoi.iohksu.domain.usecase.ObserveProfileTemplatesUseCase
import com.dwngkhoi.iohksu.domain.usecase.ObserveStartupStateUseCase
import com.dwngkhoi.iohksu.domain.usecase.ObserveSulogStateUseCase
import com.dwngkhoi.iohksu.domain.usecase.ObserveSuperUserStateUseCase
import com.dwngkhoi.iohksu.domain.usecase.ObserveUmountStateUseCase
import com.dwngkhoi.iohksu.domain.usecase.RebootUseCase
import com.dwngkhoi.iohksu.domain.usecase.RefreshDynamicManagerUseCase
import com.dwngkhoi.iohksu.domain.usecase.RefreshInstalledModulesUseCase
import com.dwngkhoi.iohksu.domain.usecase.RefreshModuleCatalogUseCase
import com.dwngkhoi.iohksu.domain.usecase.RefreshProfileTemplatesUseCase
import com.dwngkhoi.iohksu.domain.usecase.RefreshSulogUseCase
import com.dwngkhoi.iohksu.domain.usecase.RefreshSuperUsersUseCase
import com.dwngkhoi.iohksu.domain.usecase.RefreshUmountPathsUseCase
import com.dwngkhoi.iohksu.domain.usecase.RemovePreferenceUseCase
import com.dwngkhoi.iohksu.domain.usecase.RemoveUmountPathUseCase
import com.dwngkhoi.iohksu.domain.usecase.SaveModuleActionLogUseCase
import com.dwngkhoi.iohksu.domain.usecase.SaveProfileTemplateUseCase
import com.dwngkhoi.iohksu.domain.usecase.SelectDynamicManagerUseCase
import com.dwngkhoi.iohksu.domain.usecase.SetAppProfileUseCase
import com.dwngkhoi.iohksu.domain.usecase.SetAppSepolicyUseCase
import com.dwngkhoi.iohksu.domain.usecase.SetBooleanPreferenceUseCase
import com.dwngkhoi.iohksu.domain.usecase.SetDefaultUmountModulesUseCase
import com.dwngkhoi.iohksu.domain.usecase.SetKernelUmountEnabledUseCase
import com.dwngkhoi.iohksu.domain.usecase.SetManualDynamicManagerUseCase
import com.dwngkhoi.iohksu.domain.usecase.SetModuleEnabledUseCase
import com.dwngkhoi.iohksu.domain.usecase.SetModuleRemovedUseCase
import com.dwngkhoi.iohksu.domain.usecase.SetSelinuxHideEnabledUseCase
import com.dwngkhoi.iohksu.domain.usecase.SetStringPreferenceUseCase
import com.dwngkhoi.iohksu.domain.usecase.SetStringSetPreferenceUseCase
import com.dwngkhoi.iohksu.domain.usecase.SetSuEnabledUseCase
import com.dwngkhoi.iohksu.domain.usecase.SetWebViewZygoteUmountEnabledUseCase
import com.dwngkhoi.iohksu.domain.usecase.StartKernelFlashUseCase
import com.dwngkhoi.iohksu.domain.usecase.SuSFSConfigUseCase
import com.dwngkhoi.iohksu.domain.usecase.TakeModuleUriPermissionUseCase
import com.dwngkhoi.iohksu.domain.usecase.TransliterateTextUseCase
import com.dwngkhoi.iohksu.domain.usecase.UpdateAppearanceUseCase
import com.dwngkhoi.iohksu.domain.usecase.UpdateCachedModuleEnabledUseCase
import com.dwngkhoi.iohksu.domain.usecase.UpdatePlatformSettingUseCase
import com.dwngkhoi.iohksu.domain.usecase.ValidateSepolicyUseCase
import com.dwngkhoi.iohksu.ui.activity.util.ThemeUtils
import com.dwngkhoi.iohksu.ui.component.ZipFileDetector
import com.dwngkhoi.iohksu.ui.theme.BackgroundManager
import com.dwngkhoi.iohksu.ui.theme.CardConfig
import com.dwngkhoi.iohksu.ui.theme.ThemeConfig
import com.dwngkhoi.iohksu.ui.util.module.Shortcut
import com.dwngkhoi.iohksu.ui.viewmodel.AppProfileViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.DynamicManagerViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.ExecuteModuleActionViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.FlashViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.HomeViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.InstallViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.KernelFlashViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.MainIntentViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.ModuleDetailViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.ModuleRepoViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.ModuleViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.SettingsViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.SuSFSViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.SulogViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.SuperUserViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.TemplateEditorViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.TemplateViewModel
import com.dwngkhoi.iohksu.ui.viewmodel.UmountManagerScreenViewModel
import com.dwngkhoi.iohksu.ui.webui.MonetColorsProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import me.zhanghai.android.appiconloader.coil.AppIconFetcher
import me.zhanghai.android.appiconloader.coil.AppIconKeyer
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.File
import java.util.Locale
import java.util.concurrent.TimeUnit

val applicationScopeQualifier = named("applicationScope")

val coreModule = module {
    single<CoroutineScope>(applicationScopeQualifier) {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
    single {
        OkHttpClient.Builder()
            .cache(Cache(File(androidApplication().cacheDir, "okhttp"), 10L * 1024L * 1024L))
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .header("User-Agent", "iohkSU/${BuildConfig.VERSION_CODE}")
                        .header("Accept-Language", Locale.getDefault().toLanguageTag())
                        .build()
                )
            }
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()
    }
    single {
        val application = androidApplication()
        val iconSize = application.resources.getDimensionPixelSize(android.R.dimen.app_icon_size)
        ImageLoader.Builder(application)
            .components {
                add(AppIconKeyer())
                add(AppIconFetcher.Factory(iconSize, false, application))
            }
            .build()
    }
}

val repositoryModule = module {
    single { KsuCliRepository(androidApplication()) }
    singleOf(::InstalledPackageCache)
    singleOf(::AppIconDataSource)
    singleOf(::RootServiceRepository)
    singleOf(::InstalledPackageRepository)
    single {
        SuperUserRepository(
            application = get(),
            cache = get(),
            installedPackageRepository = get(),
            profileRepository = get(),
            applicationScope = get(applicationScopeQualifier),
        )
    }
    single {
        AppSettingsRepository(
            context = androidApplication(),
            applicationScope = get(applicationScopeQualifier),
        )
    }
    singleOf(::StartupRepository)
    single {
        ApplicationInitializationRepository(
            application = get(),
            imageLoader = get(),
            applicationScope = get(applicationScopeQualifier),
            flashRepository = get(),
            ksuCliRepository = get(),
            monetCompatColorSource = get(),
        )
    }
    singleOf(::ManagerUpdateRepository)
    singleOf(::ApplicationControlRepository)
    singleOf(::DownloadRepository)
    single { FlashRepository(get(), get(applicationScopeQualifier), get(), get()) }
    singleOf(::KernelRepository)
    singleOf(::HomeRuntimeRepository)
    singleOf(::HomeStateRepository)
    singleOf(::NetworkStatusRepository)
    singleOf(::NetworkRequestRepository)
    singleOf(::DynamicManagerRepository)
    singleOf(::SulogRepository)
    singleOf(::BugreportRepository)
    singleOf(::UmountRepository)
    singleOf(::ModuleCatalogRepository)
    singleOf(::ModuleRepository)
    singleOf(::ModulePreferencesRepository)
    singleOf(::ModuleActionRepository)
    singleOf(::WebResourceRepository)
    singleOf(::WebUiRepository)
    singleOf(::ModuleFileRepository)
    singleOf(::ProfileRepository)
    singleOf(::ProfileTemplateRepository)
    singleOf(::SuSFSConfigHelper)
    singleOf(::SuSFSRepository)
    singleOf(::MonetCompatColorSource)
    singleOf(::ThemeRepository)
    single {
        val themeRepository = get<ThemeRepository>()
        ThemeConfig(themeRepository::defaultSeedColor)
    }
    singleOf(::CardConfig)
    singleOf(::BackgroundManager)
    singleOf(::ThemeUtils)
    singleOf(::LocaleHelper)
    singleOf(::LocaleRepository)
    singleOf(::SettingsPlatformRepository)
    singleOf(::ShortcutRepository)
    singleOf(::Shortcut)
    singleOf(::MonetColorsProvider)
    singleOf(::ZipFileDetector)
    single { HanziToPinyin.create() } bind TextTransliterator::class
}

val useCaseModule = module {
    factoryOf(::InitializeApplicationUseCase)
    factoryOf(::GetHomeBasicInfoUseCase)
    factoryOf(::GetHomeModuleOverviewUseCase)
    factoryOf(::GetHomeSuperuserCountUseCase)
    factoryOf(::IsNetworkAvailableUseCase)
    factoryOf(::LoadSettingsPlatformUseCase)
    factoryOf(::UpdateAppearanceUseCase)
    factoryOf(::UpdatePlatformSettingUseCase)
    factoryOf(::GetPlatformFeatureStatusUseCase)
    factoryOf(::CheckManagerUpdateUseCase)
    factoryOf(::EnsureManagerInstalledUseCase)
    factoryOf(::RebootUseCase)
    factoryOf(::EnqueueDownloadUseCase)
    factoryOf(::EnqueueManagerUpdateUseCase)
    factoryOf(::ObserveDownloadUseCase)
    factoryOf(::GetKernelStatusUseCase)
    factoryOf(::GetInstallEnvironmentUseCase)
    factoryOf(::ExecuteFlashOperationUseCase)
    factoryOf(::CheckFlashModuleMountUseCase)
    factoryOf(::GetManagerRuntimeInfoUseCase)
    factoryOf(::GetKernelFeatureSettingsUseCase)
    factoryOf(::SetSuEnabledUseCase)
    factoryOf(::SetKernelUmountEnabledUseCase)
    factoryOf(::ConfigureSuLogUseCase)
    factoryOf(::SetSelinuxHideEnabledUseCase)
    factoryOf(::SetDefaultUmountModulesUseCase)
    factoryOf(::SetWebViewZygoteUmountEnabledUseCase)
    factoryOf(::IsLateLoadModeUseCase)
    factoryOf(::GetAppProfileUseCase)
    factoryOf(::SetAppProfileUseCase)
    factoryOf(::GetAppSepolicyUseCase)
    factoryOf(::SetAppSepolicyUseCase)
    factoryOf(::ControlAppUseCase)
    factoryOf(::ValidateSepolicyUseCase)
    factoryOf(::GetDefaultUmountModulesUseCase)
    factoryOf(::GetSuSFSStatusUseCase)
    factoryOf(::SuSFSConfigUseCase)
    factoryOf(::ApplyLanguageUseCase)
    factoryOf(::IsSystemLanguageSettingsUseCase)
    factoryOf(::LaunchSystemLanguageSettingsUseCase)
    factoryOf(::GenerateBugreportUseCase)
    factoryOf(::ObserveStartupStateUseCase)
    factoryOf(::GetSuperUserAppGroupUseCase)
    factoryOf(::ObserveCatalogModulesUseCase)
    factoryOf(::ObserveModuleCatalogRefreshingUseCase)
    factoryOf(::ObserveModuleCatalogOfflineUseCase)
    factoryOf(::RefreshModuleCatalogUseCase)
    factoryOf(::GetCatalogModuleUseCase)
    factoryOf(::ObserveProfileTemplatesUseCase)
    factoryOf(::ObserveProfileTemplateRefreshingUseCase)
    factoryOf(::ObserveProfileTemplateOfflineUseCase)
    factoryOf(::RefreshProfileTemplatesUseCase)
    factoryOf(::GetProfileTemplateUseCase)
    factoryOf(::SaveProfileTemplateUseCase)
    factoryOf(::DeleteProfileTemplateUseCase)
    factoryOf(::ImportProfileTemplatesUseCase)
    factoryOf(::ExportProfileTemplatesUseCase)
    factoryOf(::GetBooleanPreferenceUseCase)
    factoryOf(::SetBooleanPreferenceUseCase)
    factoryOf(::GetStringPreferenceUseCase)
    factoryOf(::SetStringPreferenceUseCase)
    factoryOf(::GetStringSetPreferenceUseCase)
    factoryOf(::SetStringSetPreferenceUseCase)
    factoryOf(::ObserveDynamicManagerStateUseCase)
    factoryOf(::RefreshDynamicManagerUseCase)
    factoryOf(::SelectDynamicManagerUseCase)
    factoryOf(::SetManualDynamicManagerUseCase)
    factoryOf(::ClearDynamicManagerUseCase)
    factoryOf(::ObserveSulogStateUseCase)
    factoryOf(::RefreshSulogUseCase)
    factoryOf(::EnableSulogUseCase)
    factoryOf(::CleanSulogUseCase)
    factoryOf(::ObserveUmountStateUseCase)
    factoryOf(::RefreshUmountPathsUseCase)
    factoryOf(::AddUmountPathUseCase)
    factoryOf(::RemoveUmountPathUseCase)
    factoryOf(::ObserveKernelFlashUseCase)
    factoryOf(::StartKernelFlashUseCase)
    factoryOf(::RemovePreferenceUseCase)
    factoryOf(::ObserveSuperUserStateUseCase)
    factoryOf(::RefreshSuperUsersUseCase)
    factoryOf(::BackupAllowlistUseCase)
    factoryOf(::ImportAllowlistUseCase)
    factoryOf(::FetchRemoteTextUseCase)
    factoryOf(::IsModuleUriAccessibleUseCase)
    factoryOf(::TakeModuleUriPermissionUseCase)
    factoryOf(::ExtractModuleNameUseCase)
    factoryOf(::ExtractModuleIdUseCase)
    factoryOf(::ObserveInstalledModulesUseCase)
    factoryOf(::RefreshInstalledModulesUseCase)
    factoryOf(::CalculateInstalledModuleSizeUseCase)
    factoryOf(::UpdateCachedModuleEnabledUseCase)
    factoryOf(::ExecuteModuleActionUseCase)
    factoryOf(::SaveModuleActionLogUseCase)
    factoryOf(::SetModuleEnabledUseCase)
    factoryOf(::SetModuleRemovedUseCase)
    factoryOf(::TransliterateTextUseCase)
}

val viewModelModule = module {
    viewModel { parameters ->
        AppProfileViewModel(
            uid = parameters[0],
            packageName = parameters[1],
            getAppGroup = get(),
            getProfile = get(),
            getDefaultUmountModules = get(),
            setProfile = get(),
            getSepolicy = get(),
            setSepolicy = get(),
            controlApp = get(),
            validateSepolicy = get(),
        )
    }
    viewModelOf(::HomeViewModel)
    viewModelOf(::InstallViewModel)
    viewModelOf(::MainIntentViewModel)
    viewModelOf(::KernelFlashViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::ModuleViewModel)
    viewModelOf(::SuperUserViewModel)
    viewModelOf(::SuSFSViewModel)
    viewModelOf(::ModuleRepoViewModel)
    viewModel { parameters -> ModuleDetailViewModel(parameters[0], get()) }
    viewModelOf(::TemplateViewModel)
    viewModel { parameters ->
        TemplateEditorViewModel(
            templateId = parameters[0],
            readOnly = parameters[1],
            isCreation = parameters[2],
            getTemplate = get(),
            saveTemplate = get(),
            deleteTemplate = get(),
        )
    }
    viewModelOf(::SulogViewModel)
    viewModelOf(::DynamicManagerViewModel)
    viewModelOf(::FlashViewModel)
    viewModelOf(::UmountManagerScreenViewModel)
    viewModel { parameters ->
        ExecuteModuleActionViewModel(
            moduleId = parameters[0],
            executeModuleAction = get(),
            saveModuleActionLog = get(),
        )
    }
}

val appModules = listOf(coreModule, repositoryModule, useCaseModule, viewModelModule)
