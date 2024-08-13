package com.example.firstapplicationtask.recyclerAdapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapplicationtask.data.Beneficiary
import com.example.firstapplicationtask.R

class MyAdapter(private val items: List<Beneficiary>, private val context: Context) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(val itemLayout: LinearLayout) : RecyclerView.ViewHolder(itemLayout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Create a GradientDrawable with rounded corners and a light grey background
        val backgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 16f // Rounded corners with a radius of 16dp
            setColor(Color.LTGRAY) // Light grey background color
        }

        // Create a LinearLayout to hold the item content with the custom background
        val itemLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 20, 40, 20) // Add padding (left, top, right, bottom)
            background = backgroundDrawable // Set the custom background
            layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(20, 10, 20, 10) // Add margin between items (left, top, right, bottom)
            }
        }

        // Create a TextView to display the beneficiary's information
        val textView = TextView(context).apply {
            textSize = 25f
        }
        itemLayout.addView(textView)

        return MyViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        // Concatenate the fields with a space separator
        holder.itemLayout.getChildAt(0)?.let { view ->
            if (view is TextView) {
                view.text = context.getString(
                    R.string.beneficiary_info,
                    item.firstName,
                    item.lastName,
                    item.beneType,
                    convertDesignationCode(item.designationCode)
                )
            }
        }

        holder.itemView.setOnClickListener {
            showPopupWindow(holder.itemView, item)
        }
    }

    //small helper function to convert the code caricature to the proper string or empty as a default
    private fun convertDesignationCode(code: String): String {
        return when (code) {
            "C" -> "Contingent"
            "P" -> "Primary"
            else -> "" //any unknown will result in an empty string being returned
        }
    }

    override fun getItemCount() = items.size

    private fun showPopupWindow(anchorView: View, givenBeneficiary: Beneficiary) {
        // Create a ShapeDrawable with rounded corners and a light grey background
        val backgroundDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 16f // Rounded corners with a radius of 16dp
            setColor(Color.LTGRAY) // Light grey background color
            setStroke(4, Color.DKGRAY) // Dark grey border with 4dp thickness
        }

        // Create a LinearLayout to hold the content with the custom background
        val popupLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
            background = backgroundDrawable // Set the custom background
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        // Create and add TextViews for each field inside the LinearLayout
        val ssnTextView = TextView(context).apply {
            text = "SSN: ${givenBeneficiary.socialSecurityNumber}"
            textSize = 18f
        }
        popupLayout.addView(ssnTextView)

        val dobTextView = TextView(context).apply {
            val dobFormatted = "${givenBeneficiary.dateOfBirth.substring(0, 2)}/" +
                    "${givenBeneficiary.dateOfBirth.substring(2, 4)}/" +
                    "${givenBeneficiary.dateOfBirth.substring(4, 8)}"
            text = "Date of Birth: $dobFormatted"
            textSize = 18f
        }
        popupLayout.addView(dobTextView)

        val phoneTextView = TextView(context).apply {
            // Assuming the phone number is a 10-digit string like "3035555555"
            val formattedPhoneNumber = if (givenBeneficiary.phoneNumber.length == 10) {
                "(${givenBeneficiary.phoneNumber.substring(0, 3)}) " +
                        "${givenBeneficiary.phoneNumber.substring(3, 6)}-" +
                        "${givenBeneficiary.phoneNumber.substring(6)}"
            } else {
                givenBeneficiary.phoneNumber // Fallback to original format if not 10 digits
            }

            text = "Phone: $formattedPhoneNumber"
            textSize = 18f
        }
        popupLayout.addView(phoneTextView)

        // Add the "Address:" label
        val addressLabelTextView = TextView(context).apply {
            text = context.getString(R.string.address)
            textSize = 18f
        }
        popupLayout.addView(addressLabelTextView)

        // Create and add the TextView for the address
        val addressTextView = TextView(context).apply {
            val addressBuilder = StringBuilder()
            addressBuilder.append(givenBeneficiary.beneficiaryAddress.firstLineMailing)
            addressBuilder.append("\n")
            val secondLine = givenBeneficiary.beneficiaryAddress.scndLineMailing
            if (!secondLine.isNullOrEmpty() && secondLine != "null") {
                addressBuilder.append(secondLine)
                addressBuilder.append("\n")
            }
            addressBuilder.append("${givenBeneficiary.beneficiaryAddress.city}, ")
            addressBuilder.append("${givenBeneficiary.beneficiaryAddress.stateCode} ")
            addressBuilder.append(givenBeneficiary.beneficiaryAddress.zipCode)
            addressBuilder.append("\n")
            addressBuilder.append(givenBeneficiary.beneficiaryAddress.country)
            text = addressBuilder.toString().trim()
            textSize = 18f
        }
        popupLayout.addView(addressTextView)

        // Create the PopupWindow with the LinearLayout as the content
        val popupWindow = PopupWindow(
            popupLayout,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            // Allow the popup to be dismissed by tapping outside
            isOutsideTouchable = true
            isFocusable = true
        }

        // Show the PopupWindow anchored to the clicked view
        popupWindow.showAsDropDown(anchorView, 0, 10, Gravity.CENTER)
    }
}