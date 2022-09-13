package io.h3llo.appmarket.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import io.h3llo.appmarket.MainMenuActivity
import io.h3llo.appmarket.R
import io.h3llo.appmarket.core.SecurityPreferences.encryptPreferences
import io.h3llo.appmarket.core.SecurityPreferences.getToken
import io.h3llo.appmarket.core.SecurityPreferences.saveToken
import io.h3llo.appmarket.databinding.FragmentLoginBinding
import io.h3llo.appmarket.util.Constantes
import java.security.KeyStore


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel : LoginViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false )
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        events()
        observablesSetup()
    }

    private fun init() {

    }

    private fun events() = with(binding) {
        btnIngresar.setOnClickListener{
            authCredentials()
        }

        tvCrearCuenta.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    private fun observablesSetup() {
        viewModel.loader.observe(viewLifecycleOwner, Observer { condition ->
            if(condition)binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner, Observer{ error ->
            Toast.makeText(requireContext(), error,Toast.LENGTH_SHORT).show()
        })

        viewModel.token.observe(viewLifecycleOwner, Observer{ token ->
            // GUARDAR EL TOKEN LOCALMENTE ENCRIPTADO
            // Toast.makeText(requireContext(), token,Toast.LENGTH_SHORT).show()
            /*
            requireContext().
                getSharedPreferences(Constantes.PREFERENCES_TOKEN,0).
                edit().
                putString(Constantes.TOKEN_KEY,token).apply()
                */
            saveToken(token,requireContext().encryptPreferences(Constantes.PREFERENCES_TOKEN))
        })

        viewModel.user.observe(viewLifecycleOwner, Observer{ user  ->
            // NAVEGAR HACIA EL MENU
            user?.let{
                startActivity(Intent(requireContext(), MainMenuActivity::class.java))
            }

            Toast.makeText(requireContext(), user.nombres,Toast.LENGTH_SHORT).show()
        })
    }

    private fun authCredentials() = with(binding) {
        val email = edtCorreo.editText?.text.toString()
        val password = edtClave.editText?.text.toString()

        viewModel.auth(email, password)

        // USAR RETROFIT
        /*
        val response = Api.build().autenticar(LoginRequest(email,password,""))
        response.enqueue(object : Callback<LoginDto>{
            override fun onResponse(call: Call<LoginDto>, response: Response<LoginDto>) {
                if(response.isSuccessful){ // 200

                    val loginDto = response.body()

                    // LET
                    loginDto?.let {
                        Toast.makeText(requireContext(),response.code().toString()+ " " + it.message, Toast.LENGTH_SHORT).show()
                    }

                    println("Hola RETROFIT")


                }else{ // 400, 401, 500

                    if(response.code() == 401){ // NO AUTORIZADO

                    } else {

                    }
                    Toast.makeText(requireContext(),response.code().toString() + " " + response.message().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginDto>, t: Throwable) {
                Toast.makeText(requireContext(),t.message, Toast.LENGTH_SHORT).show()
            }

        })

        println("Hola FUERA DE RETROFIT")
        */

        // USAR COROUTINAS
        /*

        GlobalScope.launch(Dispatchers.Main)  {
            // HILO MAIN
            try{
                val response = withContext(Dispatchers.IO){
                    // HILO APARTE - TAREAS DE DURACION LARGA
                    Api.build().autenticar(LoginRequest(email,password,""))
                }

                // HILO MAIN
                if(response.isSuccessful){ // 200
                    val loginDto = response.body()
                    // LET
                    loginDto?.let {
                        Toast.makeText(requireContext(),response.code().toString()+ " " + it.message, Toast.LENGTH_SHORT).show()
                    }
                }else{ // 400, 401, 500
                    if(response.code() == 401){ // NO AUTORIZADO

                    } else {

                    }
                    Toast.makeText(requireContext(),response.code().toString() + " " + response.message().toString(), Toast.LENGTH_SHORT).show()
                }
            } catch (ex: Exception){
                Toast.makeText(requireContext(), ex.message,Toast.LENGTH_SHORT).show()
            }

        }

         */

        // MVVM



    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}