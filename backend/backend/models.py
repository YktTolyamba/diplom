from django.db import models

from django.contrib.auth.models import AbstractUser


class User(AbstractUser):
    second_name = models.CharField('отчество', max_length=30, blank=True, default='')

    class Meta:
        verbose_name = 'пользователь'
        verbose_name_plural = 'пользователи'
        ordering = ['last_name', 'first_name', 'second_name']

    def __str__(self):
        parts = [self.last_name or self.username]
        if self.first_name:
            parts.append(self.first_name[0] + '.')
        if self.second_name:
            parts.append(self.second_name[0] + '.')
        return ' '.join(parts)


class Course(models.Model):
    name = models.CharField('наименование', max_length=100)
    # course_type = models.IntegerField('тип курса', choices=COURSE_TYPE)
    # semester = models.ForeignKey('Semester', on_delete=models.IGNORE, verbose_name='семестр')
    # lecture_hours = models.IntegerField('часы лекций', blank=True, default=0)
    # lab_work_hours = models.IntegerField('часы лаб. работ', blank=True, default=0)
    # practice_hours = models.IntegerField('часы практ. работ', blank=True, default=0)
    # student_work_hours = models.IntegerField('часы СРС', blank=True, default=0)
    # control_hours = models.IntegerField('часы КСР', blank=True, default=0)
    # total_hours = models.IntegerField('часов всего', blank=True, default=0)

    class Meta:
        verbose_name = 'дисциплина'
        verbose_name_plural = 'дисциплины'
        ordering = ['name']

    def __str__(self):
        return self.name

    # def get_total_hours(self):
    #     return (self.lecture_hours + self.lab_work_hours + self.practice_hours +
    #             self.student_work_hours + self.control_hours)


class CourseElement(models.Model):
    kind = 'element'

    course = models.ForeignKey('Course', on_delete=models.CASCADE, verbose_name='курс обучения')
    code = models.CharField('код', max_length=10, default='')
    name = models.CharField('название', max_length=150, default='')
    modified = models.DateTimeField('дата и время изменения', auto_now=True)

    class Meta:
        abstract = True
        ordering = ['course', 'code']


class Tag(models.Model):

    name = models.CharField('название тега', max_length=150, default='')

    def __str__(self):
        return self.name

    class Meta:
        verbose_name = 'Тег'
        verbose_name_plural = 'Теги'
        ordering = ['name']


class CourseTopic(CourseElement):
    kind = 'topic'

    text = models.TextField('текст в формате Markdown', blank=True, default='')
    tag = models.ManyToManyField(Tag)

    class Meta:
        verbose_name = 'дисциплина: теоретический материал'
        verbose_name_plural = 'дисциплины: теоретические материалы'

    def __str__(self):
        return '%s: %s %s' % (self.course.name, self.code, self.name)