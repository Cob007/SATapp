package com.nycschools.app.util

import androidx.recyclerview.widget.DiffUtil
import com.nycschools.app.model.ApiResponse

class DiffUtils(
    private val mOldList: List<ApiResponse>,
    private val mNewList: List<ApiResponse>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int =
        mOldList.size


    override fun getNewListSize(): Int =
        mNewList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee: ApiResponse = mOldList[oldItemPosition]
        val newEmployee: ApiResponse = mNewList[newItemPosition]
        return oldEmployee.schoolName.equals(newEmployee.schoolName)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}