import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss']
})
export class ButtonComponent implements OnInit {

  @Input() label: string = "label";
  @Input() isDisabled: boolean = false;
  @Input() bgColor: string = "white";
  @Input() color: string = "black";
  @Input() border: string = "1px solid black";
  @Input() minWidth: string = "22rem";
  @Input() minHeight: string = "3.8rem";
  @Input() fontWeight: string = "bold";
  @Output() buttonEvent = new EventEmitter<string>();

  constructor() { }

  ngOnInit(): void {
  }

  get buttonStyles() {
    return {
      'background-color': this.bgColor,
      'color' : this.color,
      'cursor': 'pointer',
      'border' : this.border,
      'minWidth' : '100%',
      'minHeight' : '100%',
      'fontWeight': this.fontWeight

    }
  }

  onClick(): void {
    this.buttonEvent.emit(this.label);
  }

}
