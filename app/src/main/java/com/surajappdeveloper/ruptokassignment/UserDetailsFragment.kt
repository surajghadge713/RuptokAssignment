package com.surajappdeveloper.ruptokassignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.surajappdeveloper.ruptokassignment.activity.MainActivity
import com.surajappdeveloper.ruptokassignment.databinding.FragmentUserDetailsBinding
import com.surajappdeveloper.ruptokassignment.databinding.FragmentUsersBinding
import com.surajappdeveloper.ruptokassignment.model.UsersModelItem


class UserDetailsFragment : Fragment() {

    private lateinit var fragmentUserDetailsBinding: FragmentUserDetailsBinding

    private val user by lazy {
        requireArguments().getSerializable("userItem") as UsersModelItem
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val view = inflater.inflate(R.layout.fragment_user_details, container, false)

        fragmentUserDetailsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_details, container, false)

        fragmentUserDetailsBinding.usersModelItem = user

        return fragmentUserDetailsBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).activityMainBinding.toolBar.visibility = View.VISIBLE
        (requireActivity() as MainActivity).activityMainBinding.toolBar.title = "User Details"
    }


}