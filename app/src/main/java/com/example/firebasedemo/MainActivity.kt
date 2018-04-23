package com.example.firebasedemo

//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.PhoneAuthCredential
//import com.google.firebase.auth.PhoneAuthProvider
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import org.apache.poi.hssf.usermodel.HSSFRichTextString
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.simpleName

//    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        firebaseAuth = FirebaseAuth.getInstance()

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        } else {
            excelOperation()
        }

        /*  FirebaseAuth.getInstance().sendPasswordResetEmail("mwshubham@gmail.com")
                  .addOnCompleteListener { task ->
                      Log.d(TAG, "task: $task")
                      Log.d(TAG, "task: ${task.isSuccessful}")
                      task.exception?.printStackTrace()
                      if (task.isSuccessful) {
                          Log.d(TAG, "Email sent.")
                      }
                  }*/

//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                "+918285453910",        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                this,               // Activity (for callback binding)
//                callbacks   // OnVerificationStateChangedCallbacks
//        )
    }

    private fun excelOperation() {
        val workbook = HSSFWorkbook()
        val firstSheet = workbook.createSheet("Sheet No 1")
        val secondSheet = workbook.createSheet("Sheet No 2")
        val rowA = firstSheet.createRow(0)
        val cellA = rowA.createCell(0)
        cellA.setCellValue(HSSFRichTextString("Sheet One"))
        val rowB = secondSheet.createRow(0)
        val cellB = rowB.createCell(0)
        cellB.setCellValue(HSSFRichTextString("Sheet two"))
        var fos: FileOutputStream? = null
        try {
            val str_path = Environment.getExternalStorageDirectory().toString()
            Log.d(TAG, str_path)
            val file: File
            file = File(str_path, getString(R.string.app_name) + ".xls")
            Log.d(TAG, file.path)
            fos = FileOutputStream(file)
            workbook.write(fos)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.flush()
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            Toast.makeText(this@MainActivity, "Excel Sheet Generated", Toast.LENGTH_SHORT).show()
        }

    }

//    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        override fun onVerificationCompleted(credential: PhoneAuthCredential?) {
//            Log.d(TAG, "onVerificationCompleted: $credential")
//            Toast.makeText(application, "Verification completed", Toast.LENGTH_SHORT).show()
//        }
//
//        override fun onVerificationFailed(e: FirebaseException?) {
//            Log.w(TAG, "onVerificationFailed", e)
//        }
//
//    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> excelOperation()
        }
    }
}

