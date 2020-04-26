package org.dxworks.inspectorgit.web.controllers

import org.dxworks.inspectorgit.services.SystemService
import org.dxworks.inspectorgit.services.dto.SystemDTO
import org.dxworks.inspectorgit.web.apiPath
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$apiPath/system")
class SystemController(private val systemService: SystemService) {
    @PostMapping("create")
    fun create(@RequestBody systemDTO: SystemDTO) {
        systemService.create(systemDTO)
    }

    @GetMapping
    fun getAllSystems(): List<SystemDTO> {
        return systemService.findAll()
    }

    @DeleteMapping
    fun delete(@RequestParam(required = true) systemId: String) {
        this.systemService.delete(systemId)
    }
}