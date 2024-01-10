package com.solar.data.remote.dto

import com.solar.domain.model.StationListRecord

fun StationListRecordData.toStationListRecord(): StationListRecord {
    return StationListRecord(id)
}