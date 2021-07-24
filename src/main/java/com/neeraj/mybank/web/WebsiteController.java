package com.neeraj.mybank.web;

import com.neeraj.mybank.service.TransactionService;
import com.neeraj.mybank.web.forms.TransactionForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class WebsiteController {

    private final TransactionService transactionService;
    private final String bankSlogan;

    public WebsiteController(TransactionService transactionService,@Value("${bank.slogan}") String bankSlogan) {
        this.transactionService = transactionService;
        this.bankSlogan = bankSlogan;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("slogan", bankSlogan);
        return "index.html";
    }

    @GetMapping("/account/{userId}")
    public String transactionList(Model model, @PathVariable String userId){
        model.addAttribute("userId", userId);
        model.addAttribute("transactions", transactionService.findAllByReceivingUserId(userId));
        model.addAttribute("txForm", new TransactionForm());
        return "account.html";
    }

    @PostMapping("/account/{userId}")
    public String newTransaction(@ModelAttribute("txForm") @Valid TransactionForm transactionForm, BindingResult bindingResult, @PathVariable String userId, Model model){
        model.addAttribute("userId", userId);
        model.addAttribute("transactions", transactionService.findAllByReceivingUserId(userId));

        if(bindingResult.hasErrors()){
            model.addAttribute("txError", true);
            return "account.html";
        }

        createTransaction(transactionForm);
        return "redirect:/account/" + userId;
    }

    private void createTransaction(TransactionForm transactionForm){
        transactionService.create(transactionForm.getAmount(), transactionForm.getReference(), transactionForm.getReceivingUserId());
    }

}
