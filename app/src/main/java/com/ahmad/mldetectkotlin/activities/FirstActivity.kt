package com.ahmad.mldetectkotlin.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ahmad.mldetectkotlin.R
import java.util.ArrayList

class FirstActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

    }
    private val runtimePermissions: Unit
        get() {
            val allNeededPermissions: MutableList<String?> = ArrayList()
            for (permission in requiredPermissions) {
                if (!isPermissionGranted(this, permission)) {
                    allNeededPermissions.add(permission)
                }
            }
            if (allNeededPermissions.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    allNeededPermissions.toTypedArray(),
                    FirstActivity.PERMISSION_REQUESTS
                )
            }
        }
    fun MoveToMain(view: View) {
        if (!allPermissionsGranted()) {
            runtimePermissions
        }
        else
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private val requiredPermissions: Array<String?>
        get() = try {
            val info = this.packageManager
                .getPackageInfo(this.packageName, PackageManager.GET_PERMISSIONS)
            val ps = info.requestedPermissions
            if (ps != null && ps.isNotEmpty()) {
                ps
            } else {
                arrayOfNulls(0)
            }
        } catch (e: Exception) {
            arrayOfNulls(0)
        }
    private fun allPermissionsGranted(): Boolean {
        for (permission in requiredPermissions) {
            if (!isPermissionGranted(this, permission)) {
                return false
            }
        }
        return true
    }
    public fun isPermissionGranted(
        context: Context,
        permission: String?
    ): Boolean {
        if (ContextCompat.checkSelfPermission(context, permission!!)
            == PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("permit", "Permission granted: $permission")

            return true
        }
        Log.i("permit", "Permission NOT granted: $permission")
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i("permit", "Permission granted!")
        if (allPermissionsGranted()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        //bindAllCameraUseCases()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val PERMISSION_REQUESTS = 1
    }
}