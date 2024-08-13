package com.example.firstapplicationtask.data

import android.content.Context
import org.json.JSONArray

/**
 * Defaulting strings that have read errors to an empty string.
 * Better to show nothing than to crash or show junk data
 * */
class BeneficiariesParser {
    fun parseJsonFile(context: Context): List<Beneficiary> {
        val beneficiaries = mutableListOf<Beneficiary>()

        try {
            // Read JSON file from assets
            val jsonString = context.assets.open("Beneficiaries.json").bufferedReader().use { it.readText() }

            // Parse the JSON array
            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                val beneficiaryAddressJson = jsonObject.getJSONObject("beneficiaryAddress")
                val beneficiaryAddress = BeneficiaryAddress(
                    firstLineMailing = beneficiaryAddressJson.optString("firstLineMailing", ""),
                    scndLineMailing = beneficiaryAddressJson.optString("scndLineMailing", ""),
                    city = beneficiaryAddressJson.optString("city", ""),
                    zipCode = beneficiaryAddressJson.optString("zipCode", ""),
                    stateCode = beneficiaryAddressJson.optString("stateCode", ""),
                    country = beneficiaryAddressJson.optString("country", "")
                )

                val beneficiary = Beneficiary(
                    lastName = jsonObject.optString("lastName", ""),
                    firstName = jsonObject.optString("firstName", ""),
                    designationCode = jsonObject.optString("designationCode", ""),
                    beneType = jsonObject.optString("beneType", ""),
                    socialSecurityNumber = jsonObject.optString("socialSecurityNumber", ""),
                    dateOfBirth = jsonObject.optString("dateOfBirth", ""),
                    middleName = jsonObject.optString("middleName", ""),
                    phoneNumber = jsonObject.optString("phoneNumber", ""),
                    beneficiaryAddress = beneficiaryAddress
                )

                beneficiaries.add(beneficiary)
            }
        } catch (e: Exception) {
            //Could also extend this to include multiple more specific catches above
            //and still have this hard catch at the end as a safeguard

            //can do some extra logging in here if needed.
            e.printStackTrace()
        }

        return beneficiaries
    }
}