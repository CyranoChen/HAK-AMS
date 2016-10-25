package com.nlia.fqdb.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nlia.fqdb.entity.base.Terminal;
import com.nlia.fqdb.service.impl.TerminalManager;

@Controller
@RequestMapping("terminal")
public class TerminalController{

	@Resource
	private TerminalManager terminalManager;
	
	@RequestMapping(value = "findByTerminalCode/{terminalCode}", method = RequestMethod.GET)
	protected @ResponseBody
	List<Terminal> findByTerminalCode(@PathVariable String terminalCode) {
		return terminalManager.findByTerminalCode(terminalCode);
	}
	@RequestMapping(value = "", method = RequestMethod.GET)
	protected @ResponseBody
	List<Terminal> findAllTerminal() {
		return terminalManager.findAllTerminal();
	}

}