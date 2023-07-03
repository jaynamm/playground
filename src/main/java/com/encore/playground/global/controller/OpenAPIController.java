package com.encore.playground.global.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * OpenAPI 페이지 컨트롤러
 */
@Controller
@RequestMapping("/apiui/temp")
@Tag(name = "Playground", description = "플레이그라운드 API 문서")
public class OpenAPIController {
}
