export interface Article {
  id: number;
  title: string;
  content: string;
  name: string;
  userId: number;
  themeTitle: string;
  themeId: number;
  commentIds: number[];
  created_at: Date;
  updated_at: Date;
}

export interface Comment {
  id: number;
  content: string;
  userId: number;
  name: string;
  articleId: number;
  created_at: Date;
  updated_at: Date;
}
