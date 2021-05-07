package com.surajappdeveloper.ruptokassignment.users.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.surajappdeveloper.ruptokassignment.R
import com.surajappdeveloper.ruptokassignment.activity.MainActivity
import com.surajappdeveloper.ruptokassignment.databinding.FragmentUsersBinding
import com.surajappdeveloper.ruptokassignment.databinding.ItemUserRequestCardBinding
import com.surajappdeveloper.ruptokassignment.helper.GenericRecyclerViewAdapter
import com.surajappdeveloper.ruptokassignment.model.UsersModelItem
import com.surajappdeveloper.ruptokassignment.users.repository.UserRepository
import com.surajappdeveloper.ruptokassignment.users.viewmodel.UsersViewModel


class UsersFragment : Fragment() {

    private val TAG = "UsersFragment"

    private lateinit var fragmentUsersBinding: FragmentUsersBinding
    private lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        if(!::fragmentUsersBinding.isInitialized){
            fragmentUsersBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_users, container,false)
            viewModel = ViewModelProvider(requireActivity()).get(UsersViewModel::class.java)
            fragmentUsersBinding.viewModel = viewModel
            viewModel.setRepository(UserRepository(requireContext()))
            initObserver()
            viewModel.getUsers()
        }


        return fragmentUsersBinding.root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (requireActivity() as MainActivity).activityMainBinding.toolBar.visibility = View.VISIBLE
        (requireActivity() as MainActivity).activityMainBinding.toolBar.title = "User List"

    }

    private fun initObserver() {
        viewModel.usersModel.observe(requireActivity(), Observer {
            Log.d(TAG, "initObserver: $it")
            fragmentUsersBinding.rvUsers.adapter = object :
                GenericRecyclerViewAdapter<UsersModelItem, ItemUserRequestCardBinding>(
                    requireContext(),
                    it as ArrayList<UsersModelItem>
                ){
                override val layoutResId: Int
                    get() = R.layout.item_user_request_card

                override fun onBindData(
                    model: UsersModelItem?,
                    position: Int?,
                    dataBinding: ItemUserRequestCardBinding?
                ) {
                    dataBinding?.usersModelItem = model
                    dataBinding?.executePendingBindings()
                }

                override fun onItemClick(model: UsersModelItem?, position: Int?) {
                    model?.let {
                        val directions = UsersFragmentDirections.actionUsersFragmentToUserDetailsFragment(it)
                        findNavController().navigate(directions)
                    }

                }

            }
        })
    }




}