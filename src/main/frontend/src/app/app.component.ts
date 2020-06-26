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
  alwaysShowTranslation = false;
  showOnlyStars = false;

  constructor(private readonly dataService: DataService) {

  }

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.isLoading = true;
    this.dataService.getData().subscribe(words => {
      this.wordsOrigin = words;

      this.wordsInit(null, null, this.showOnlyStars);

      this.isLoading = false;
    });
  }

  wordsInit(min: number, max: number, onlyStars: boolean): void {
    if (max == null) {
      this.words = this.wordsOrigin.filter(item => true);
    } else {
      this.words = this.wordsOrigin.filter((item, index) => index >= (min || 0) && index <= max);
    }

    if (onlyStars) {
      this.words = this.words.filter(item => item[2] === '1');
    }

    this.index = this.randomInt(0, this.words.length);
  }

  nextWord(): void {
    if (!this.showTranslation) {
      this.words.splice(this.index, 1);
    }

    this.showTranslation = false;

    this.index = this.randomInt(0, this.words.length);
  }

  randomInt(min: number, max: number): number {
    return min + Math.floor((max - min) * Math.random());
  }

  reset(): void {
    this.wordsInit(this.min, this.max, this.showOnlyStars);
    this.showTranslation = false;
  }

  onStarClick(curIndex: number, event): void {
    event.preventDefault();
    event.stopPropagation();

    const index = this.wordsOrigin.findIndex(item => item[0] === this.words[curIndex][0]);
    const data = this.wordsOrigin[index][2] === '1' ? '' : '1';

    this.dataService.setData(`C${index + 1}`, data).subscribe(resp => {
      this.wordsOrigin[index][2] = data;
      this.words[index][2] = data;
    });
  }

  onShowOnlyStarsClicked(): void {
    this.showOnlyStars = !this.showOnlyStars;
    this.wordsInit(this.min, this.max, this.showOnlyStars);
  }

}
