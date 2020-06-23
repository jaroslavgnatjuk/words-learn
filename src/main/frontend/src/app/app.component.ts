import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/app/data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  wordsOrigin: Array<Array<string>>;
  words: Array<Array<string>>;
  index: number;
  min: number;
  max: number;
  showTranslation: boolean;
  isLoading: boolean;

  constructor(private readonly dataService: DataService) {

  }

  ngOnInit(): void {
    this.wordsInit(null, null);
  }

  wordsInit(min: number, max: number): void {
    this.isLoading = true;
    this.dataService.getData().subscribe(words => {
      if (max == null) {
        this.words = words;
      } else {
        this.words = words.filter((item, index) => index >= (min || 0) && index <= max);
      }

      this.wordsOrigin = Object.assign({}, words);

      this.index = this.randomInt(0, this.words.length - 1);

      this.isLoading = false;
    });
  }

  nextWord(): void {
    this.words.splice(this.index, 1);
    this.showTranslation = false;


    this.index = this.randomInt(0, this.words.length - 1);
  }

  randomInt(min: number, max: number): number {
    return min + Math.floor((max - min) * Math.random());
  }

  reset(): void {
    this.wordsInit(this.min, this.max);
    this.showTranslation = false;
  }

}
