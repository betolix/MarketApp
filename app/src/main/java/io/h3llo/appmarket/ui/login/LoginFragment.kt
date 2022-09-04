package io.h3llo.appmarket.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

        binding.btnIngresar.setOnClickListener{
            authCredentials()
        }

        viewModel.loader.observe(viewLifecycleOwner, Observer { condition ->
            if(condition)binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        })

        viewModel.error.observe(viewLifecycleOwner, Observer{ error ->
             Toast.makeText(requireContext(), error,Toast.LENGTH_SHORT).show()
        })

        viewModel.token.observe(viewLifecycleOwner, Observer{ token ->
            // GUARDAR EL TOKEN LOCALMENTE ENCRIPTADO
            Toast.makeText(requireContext(), token,Toast.LENGTH_SHORT).show()

            requireContext().
                getSharedPreferences(Constantes.PREFERENCES_TOKEN,0).
                edit().
                putString(Constantes.TOKEN_KEY,token).apply()
        })

        viewModel.user.observe(viewLifecycleOwner, Observer{ user  ->
            // NAVEGAR HACIA EL MENU
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