package me.chriskyle.ikan.presentation.module.account.login

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/25.
 */
@Singleton
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {

    fun inject(loginFragment: LoginFragment)
}