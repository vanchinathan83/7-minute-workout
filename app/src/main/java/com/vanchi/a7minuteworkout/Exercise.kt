package com.vanchi.a7minuteworkout

class Exercise(
    private var id: Int,
    private var name: String,
    private var image: Int,
    private var isCompleted: Boolean = false,
    private var isSelected: Boolean = false
) {
    fun getId() = this.id
    fun setId(id : Int) {
        this.id = id
    }

    fun getName() = this.name
    fun setName(name: String) {
        this.name = name
    }

    fun getImage() = this.image
    fun setImage(image: Int) {
        this.image = image
    }

    fun getIsCompleted() = this.isCompleted
    fun setIsCompleted(isCompleted: Boolean){
        this.isCompleted = isCompleted
    }

    fun getIsSelected() = this.isSelected
    fun setIsSelected(isSelected: Boolean){
        this.isSelected = isSelected
    }
}