package com.vanchi.a7minuteworkout

object Constants {

    fun getExercises() : ArrayList<Exercise> {
        val exercises = ArrayList<Exercise>()
        val jumpingJacks = Exercise(1,"Jumping Jacks", R.drawable.jumping_jacks)
        val pushUps = Exercise(2,"Push Ups", R.drawable.pushups)
        val planks = Exercise(3,"Plank", R.drawable.plank)
        val lunge = Exercise(4,"Lunges", R.drawable.lunge)
        val sitUps = Exercise(5, "Sit Ups", R.drawable.sit_up)
        exercises.add(jumpingJacks)
        exercises.add(pushUps)
        exercises.add(planks)
        exercises.add(lunge)
        exercises.add(sitUps)
        return  exercises
    }
}