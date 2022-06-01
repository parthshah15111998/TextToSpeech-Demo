package com.example.texttospeechdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeechdemo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityMainBinding
    private var tts:TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tts= TextToSpeech(this,this)

        binding.btnPress.setOnClickListener {
            if (binding.etWriteText.text!!.isEmpty()){
                Toast.makeText(this,"Enter the Text",Toast.LENGTH_SHORT).show()
            }else{
                speakOut(binding.etWriteText.text!!.toString())
            }
        }
    }

    override fun onInit(p0: Int) {
            if (p0 == TextToSpeech.SUCCESS){
                val result= tts?.setLanguage(Locale.US)

                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("TTS","Language Is Not Support")
                }
            }else{
                Log.e("TTS","Failed")
            }
    }

    private fun speakOut(text:String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tts != null){
            tts?.stop()
            tts?.shutdown()
        }
    }

}