package com.example.myfypproject.Fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.myfypproject.Base.BaseFragment
import com.example.myfypproject.R
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class QRCodeFragment:BaseFragment(),ZXingScannerView.ResultHandler{
    private  lateinit var zXingScannerView: ZXingScannerView
    override fun FragmentCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        zXingScannerView = ZXingScannerView(activity)
        var viewGroup = view?.findViewById<ViewGroup>(R.id.content_frame)
        viewGroup?.addView(zXingScannerView)
        return zXingScannerView
    }

    override fun FragmentCreatedView(view: View, savedInstanceState: Bundle?) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
        }
        else if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){

        }
    }
    override fun attachObserver() {
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(permissionCheck(requestCode,grantResults)){
            initScanner()
        }
    }

    override fun handleResult(rawResult: Result?) {
        baseToastMessage(rawResult.toString())
        handleScannedResult(rawResult!!)
    }

    private fun handleScannedResult(result: Result){
        val scanResult = result
        if(!scanResult.toString().isNullOrEmpty()){
        }
    }
    private fun permissionCheck(requestCode: Int, grantResults: IntArray):Boolean{
        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                return true
            }
        }
        return false
    }

    private fun initScanner(){
        zXingScannerView.setResultHandler(this)
        zXingScannerView.startCamera()
    }

}