package main

import utils.loadInputForDay
import utils.loadTestInputForDay

typealias InputsAndOutputs = Pair<List<String>, List<String>>
typealias FixedPatternAndValue = Pair<String, Int>

val UNIQUE_PATTERNS = mapOf(
    2 to 1, 3 to 7, 4 to 4, 7 to 8
)

fun parseInputsAndOutputs(input: List<String>): List<InputsAndOutputs> {
    return input.map {
        val line = it.split(" | ")
        Pair(line.first().split(" "), line.last().split(" "))
    }
}

fun patternIsUnique(pattern: String): Boolean {
    return pattern.length in UNIQUE_PATTERNS.keys
}

fun firstSegmentNotIn(strings: List<String>, chars: Set<Char>): Char? {
    for (char in chars) {
        for (str in strings) {
            if (!str.contains(char)) return char
        }
    }

    return null
}

fun containSameSegments(str1: String, str2: String): Boolean {
    if (str1.length != str2.length) return false

    for (char in str1) {
        if (char !in str2) return false
    }

    return true
}

// Very inefficient, but works ;)
fun resolvePatterns(patterns: List<String>): List<FixedPatternAndValue> {
    val uniquePatterns = patterns.filter { !patternIsUnique(it) }.toMutableList()
    val segmentsFrequency = uniquePatterns.joinToString("").groupingBy { it }.eachCount()
    // separate by number of segments
    val patterns235 = uniquePatterns.filter { it.length == 5 }
    val patterns069 = uniquePatterns.filter { it.length == 6 }

    // this segment has frequency 3 and is unique to 0, 2 and 6
    val bottomLeftSegment = segmentsFrequency.filter { it.value == 3 }.keys.first()
    val twoPattern = patterns235.first { it.contains(bottomLeftSegment) }
    val ninePattern = patterns069.first { !it.contains(bottomLeftSegment)}

    // these segments have frequency 5 and appear in all numbers except 0 and 2
    val segmentsWithFreq5 = segmentsFrequency.filter { it.value == 5 }.keys
    val segmentNotIn0 = firstSegmentNotIn(patterns069, segmentsWithFreq5)!!
    val zeroPattern = patterns069.first { !it.contains(segmentNotIn0) }

    // can only be 6
    val sixPattern = patterns069.first {it != zeroPattern && it != ninePattern}

    // these segments have frequency 4 and one of them is in 5 and 6
    val segmentsWithFreq4 = segmentsFrequency.filter { it.value == 4 }.keys
    val segmentIn5And6 = segmentsWithFreq4.first { sixPattern.contains(it) }
    val fivePattern = patterns235.first { it.contains(segmentIn5And6) }

    // can only be 3
    val threePattern = patterns235.first {it != twoPattern && it != fivePattern}

    return listOf(
        Pair(zeroPattern, 0),
        Pair(twoPattern, 2),
        Pair(threePattern, 3),
        Pair(fivePattern, 5),
        Pair(sixPattern, 6),
        Pair(ninePattern, 9)
    )
}

fun outputFromPattern(patterns: List<String>, resolvedPatterns: List<Pair<String, Int>>): Int {
    val numericOutput = StringBuilder(4)

    for (pattern in patterns){
        if (patternIsUnique(pattern)) {
            numericOutput.append(UNIQUE_PATTERNS[pattern.length])
        } else {
            val match = resolvedPatterns.first { (fixedPattern, _) -> containSameSegments(pattern, fixedPattern) }
            numericOutput.append(match.second)
        }
    }

    return numericOutput.toString().toInt()
}

fun main() {
    fun part1(input: List<String>): Int {
        return parseInputsAndOutputs(input)
            .map { it.second }
            .flatten()
            .count { patternIsUnique(it) }
    }

    fun part2(input: List<String>): Int {
        return parseInputsAndOutputs(input).sumOf { (displayInput, displayOutput) ->
            val resolvedPatterns = resolvePatterns(displayInput)
            outputFromPattern(displayOutput, resolvedPatterns)
        }
    }

    val testInput = loadTestInputForDay(8)
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = loadInputForDay(8)
    println(part1(input))
    println(part2(input))
}
