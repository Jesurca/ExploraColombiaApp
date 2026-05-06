package me.jesusurbinez.exploracolombiaapp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddPlaceViewModel : ViewModel(){
    var placeName by  { mutableStateOf("") }
    var department by  { mutableStateOf("") }
    var city by  { mutableStateOf("") }
    var description by  { mutableStateOf("") }


}