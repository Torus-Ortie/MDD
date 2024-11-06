import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{
  constructor() {}

  ngOnInit(): void {}

  start() {
    alert('Commencez par lire le README et à vous de jouer !');
  }
}
