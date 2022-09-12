package com.baarton.traininglog.model

import kotlin.time.Duration

/*
 * Wrapper class. KSP has problems with generating a Room TypeConverter for kotlin.time.Duration class.
 */
data class Duration(val duration: Duration)