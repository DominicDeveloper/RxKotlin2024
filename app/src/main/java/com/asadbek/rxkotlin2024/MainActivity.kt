package com.asadbek.rxkotlin2024


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.asadbek.rxkotlin2024.databinding.ActivityMainBinding
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var TAG = "MyMainActivity"
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observable - obektlarni eshitib tyradigan xususiyat
        // EditText bilan misol
        val observable = createEditTextChangeObservable()
        observable.subscribeOn(Schedulers.io())
            //.observeOn(AndroidSchedulers.mainThread()) // ishni mainthreadda bajarishi
            .debounce(5L,TimeUnit.SECONDS) // ishni 5 sekunddan keyin bajaradi. vaqtinchalik to`xtatib turadi
            // debounce - mainthreadda - view lar uchun ishlamaydi xatolik tashlaydi, subscribeda yoziladiganlarfaqat backend uchun view lar uchun handler ishlatish kerak
            .subscribe {
                Log.d(TAG, "onCreate: $it")
            }
    }
    private fun createEditTextChangeObservable(): Observable<String> {
        return Observable.create{ emitter ->
            binding.edt1.addTextChangedListener {
                emitter.onNext(it.toString())
            }
        }
    }
}