package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override operator fun compareTo(other: MyDate): Int {
        if (year != other.year) {
            return year.compareTo(other.year)
        } else if (month != other.month) {
            return month.compareTo(other.month)
        } else if (dayOfMonth != other.dayOfMonth) {
            return dayOfMonth.compareTo(other.dayOfMonth)
        } else {
            return 0
        }
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(ti: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, ti)

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

operator fun MyDate.plus(other: RepeatedTimeInterval): MyDate = addTimeIntervals(other.ti, other.n)
operator fun MyDate.plus(other: TimeInterval): MyDate = addTimeIntervals(other, 1)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override operator fun contains(value: MyDate): Boolean = value >= start && value <= endInclusive
    override fun isEmpty(): Boolean = start > endInclusive

    var current: MyDate = start
    override operator fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            override operator fun next(): MyDate {
                val result: MyDate = current
                if (hasNext()) {
                    current = current.nextDay()
                }
                return result
            }

            override operator fun hasNext(): Boolean {
                return current <= endInclusive
            }
        }
    }
}
