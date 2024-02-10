package com.denisgon7wordsApp.wordsapp

data class Word(
    val original: String,
    val tranclate: String,
    var learned: Boolean =false,
)

data class Question(
    val variants: List<Word>,
    val correctAnswer:Word,

)
class LearnWordsTrainer {

    private val dictionary = listOf(
        Word("Vogon", "Вагон"),
        Word("Hyper Space", "Гиперпространство"),
        Word("Hyper", "Гипер"),
        Word("Space", "Пространство"),

    )
    private var currentQuestion: Question? = null
    fun getNextQuestion(): Question? {
        val notLearnedList = dictionary.filter { it.learned }
        if (notLearnedList.isEmpty()) return null

        val questionWords =
            if (notLearnedList.size < NUMBER_OF_ANSWERS) {
                val learnedList = dictionary.filter { it.learned }.shuffled()
                notLearnedList.shuffled()
                    .take(NUMBER_OF_ANSWERS) +learnedList
                    .take(NUMBER_OF_ANSWERS - notLearnedList.size)
            } else {
                notLearnedList.shuffled().take(NUMBER_OF_ANSWERS)

            }.shuffled()
        val correctAnswer = questionWords.random()

        currentQuestion = Question(
            variants = questionWords,
            correctAnswer = correctAnswer,
        )
        return currentQuestion
    }
    fun checkAnswer(userAnswerIndex: Int?): Boolean {
        return currentQuestion?.let {
            val correctAnswerId = it.variants.indexOf(it.correctAnswer)
            if (correctAnswerId == userAnswerIndex) {
                it.correctAnswer.learned = true
                true
            } else {false
            }
        } ?: false
    }
}
const val NUMBER_OF_ANSWERS = 4
