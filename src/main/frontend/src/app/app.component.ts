import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/app/data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  words: Array<Array<string>>;
  index: number;

  constructor(private readonly dataService: DataService) {

  }
  ngOnInit(): void {
    this.dataService.getData().subscribe(data => {
      this.words = data;

      this.index = this.randomInt(0, this.words.length - 1);
    });
  }

  nextWord(): void {
    this.words.splice(this.index, 1);
  }

  randomInt(min: number, max: number): number {
    return min + Math.floor((max - min) * Math.random());
  }
}
