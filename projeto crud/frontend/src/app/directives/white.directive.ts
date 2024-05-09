import { Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[btnDelete]'
})
export class WhiteDirective {

  constructor(private el: ElementRef) { 
    el.nativeElement.style.color = "#fff"
    el.nativeElement.style.backgroundColor = "#ff5757"
  }  
}
