import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'ba-msg-center',
  styleUrls: ['./baMsgCenter.scss'],
  templateUrl: './baMsgCenter.html'
})
export class BaMsgCenter implements OnInit {

  public userName: string;
  public email: string;

  constructor() {
  }

  public ngOnInit(): void {
    this.userName = localStorage.getItem('name');
    this.email = localStorage.getItem('email');
  }
}
