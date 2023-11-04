package nook.test.market_nookat.ui.fragment.util


import android.content.Context
import android.content.SharedPreferences
import android.net.Uri

class Pref ( context: Context) {
    val sharedPref: SharedPreferences =
        context.getSharedPreferences("presences", Context.MODE_PRIVATE)

    fun isBoardingShowed(): Boolean {
        return sharedPref.getBoolean("board", true)
    }

    fun setBoardingShowed(isSnow: Boolean) {
        sharedPref.edit().putBoolean("board", isSnow).apply()
    }

    fun isBoardingFire(): Boolean {
        return sharedPref.getBoolean("fiwerty",true)
    }

    fun setBoardingFire(isSnow: Boolean) {
        sharedPref.edit().putBoolean("fiwerty", isSnow).apply()
    }


    fun isTitleFire(): String {
        return sharedPref.getString("firetitlwer", "").toString()
    }

    fun setTitleFire(isSnow: String) {
        sharedPref.edit().putString("firetitlwer", isSnow).apply()
    }

    fun isSwichShowed(): Boolean {
        return sharedPref.getBoolean("swich", true)
    }

    fun setSwichShowed(isSnow: Boolean) {
        sharedPref.edit().putBoolean("swich", isSnow).apply()
    }

    fun isPhoto(): String {
        return sharedPref.getString("id_ads", "").toString()
    }

    fun setPhoto(position: String) {
        sharedPref.edit().putString("id_ads", position).apply()
    }


    fun isCategory(): String {
        return sharedPref.getString("category", "").toString()
    }

    fun setCategory(isSnow: String) {
        sharedPref.edit().putString("category", isSnow).apply()
    }

    fun isCur(): String {
        return sharedPref.getString("cur", "").toString()
    }

    fun setCur(isSnow: String) {
        sharedPref.edit().putString("cur", isSnow).apply()
    }

    fun isLocation(): String {
        return sharedPref.getString("location", "").toString()
    }

    fun setLocation(isSnow: String) {
        sharedPref.edit().putString("location", isSnow).apply()
    }

    fun isCatery(): String {
        return sharedPref.getString("catery", "").toString()
    }

    fun setCatery(isSnow: String) {
        sharedPref.edit().putString("catery", isSnow).apply()
    }

    fun isCu(): String {
        return sharedPref.getString("cu", "").toString()
    }

    fun setCu(isSnow: String) {
        sharedPref.edit().putString("cu", isSnow).apply()
    }

    fun isLocati(): String {
        return sharedPref.getString("locati", "").toString()
    }

    fun setLocati(isSnow: String) {
        sharedPref.edit().putString("locati", isSnow).apply()
    }


    fun isMaxPrice(): String {
        return sharedPref.getString("priceMax", "").toString()
    }

    fun setMaxPrice(isSnow: String) {
        sharedPref.edit().putString("priceMax", isSnow).apply()
    }

    fun isMinPrice(): String {
        return sharedPref.getString("priceMin", "").toString()
    }

    fun setMinPrice(isSnow: String) {
        sharedPref.edit().putString("priceMin", isSnow).apply()
    }

    fun isLogin():String{
        return sharedPref.getString("loginm","").toString()
    }
    fun setLogin(isSnow: String){
        sharedPref.edit().putString("loginm",isSnow).apply()
    }

    fun isName():String{
        return sharedPref.getString("asdv","").toString()
    }

    fun setName(isSnow: String){
        sharedPref.edit().putString("asdv",isSnow).apply()
    }

    fun isPasword():String{
        return sharedPref.getString("paswordf","").toString()
    }

    fun setPasword(isSnow: String){
        sharedPref.edit().putString("paswordf",isSnow).apply()
    }



    fun isToken():String{
        return sharedPref.getString("token","").toString()
    }

    fun setToken(isSnow: String){
        sharedPref.edit().putString("token",isSnow).apply()
    }

    fun isTittle():String{
        return sharedPref.getString("firetittle","").toString()
    }

    fun setTittle(isSnow: String){
        sharedPref.edit().putString("firetittle",isSnow).apply()
    }

    fun isMessage():String{
        return sharedPref.getString("firemessage","").toString()
    }

    fun setMessage(isSnow: String){
        sharedPref.edit().putString("firemessage",isSnow).apply()
    }


    // Retrieve the selected language
    fun getSelectedLanguage(): String {
        return sharedPref.getString("selectedLanguage", "ru") ?: "ru"
    }

    // Set the selected language
    fun setSelectedLanguage(language: String) {
        sharedPref.edit().putString("selectedLanguage", language).apply()
    }

    fun isNotificationAlreadyShown(id: Int): Boolean {
        return sharedPref.getBoolean(id.toString(), false)
    }

    fun markNotificationAsShown(id: Int) {
        sharedPref.edit().putBoolean(id.toString(), true).apply()
    }

}