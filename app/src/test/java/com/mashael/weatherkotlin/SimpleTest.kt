package com.mashael.weatherkotlin

import com.mashael.weatherkotlin.domain.Forecast
import com.mashael.weatherkotlin.domain.ForecastDataSource
import com.mashael.weatherkotlin.domain.ForecastList
import com.mashael.weatherkotlin.domain.ForecastProvider
import com.mashael.weatherkotlin.domain.commands.RequestDayForecastCommand
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.then
import org.mockito.Mockito.`when` as whenever
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SimpleTest {
    @Test
    fun unitDataSourceReturnValue() {
        val ds = mock(ForecastDataSource::class.java)
        whenever(ds.requestDayForecast(0)).then {
            Forecast(0,0,"desc",20,0,"url")
        }
        val provider = ForecastProvider(listOf(ds))
        assertNotNull(provider.requestForecast(0))
    }

    @Test
    fun emptyDatabaseReturnsServerValue() {
        val db= mock(ForecastDataSource::class.java)

        val server = mock(ForecastDataSource::class.java)
        whenever(server.requestForecastByCoordinates(
            anyString(), any(Long::class.java)))
            .then {
                ForecastList("0","city","country", listOf())
            }

        val provider = ForecastProvider(listOf(db,server))
        assertNotNull(provider.requestByCoordinates("0",0))
    }

    @Test
    fun testProviderIsCalled(){
        val forecastProvider = mock(ForecastProvider::class.java)
        val command = RequestDayForecastCommand(2, forecastProvider)

        command.execute()
        verify(forecastProvider).requestForecast(2)
    }


}